package hous.release.android.presentation.our_rules.graph

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hous.release.android.R
import hous.release.android.presentation.our_rules.component.LoadingBar
import hous.release.android.presentation.our_rules.component.dialog.AddRuleLimitedDialog
import hous.release.android.presentation.our_rules.component.dialog.AddRuleOutDialog
import hous.release.android.presentation.our_rules.model.DetailRuleUiModel
import hous.release.android.presentation.our_rules.screen.AddRuleScreen
import hous.release.android.presentation.our_rules.screen.MainRuleScreen
import hous.release.android.presentation.our_rules.screen.UpdateRuleScreen
import hous.release.android.presentation.our_rules.screen.type.RulesScreens
import hous.release.android.presentation.our_rules.viewmodel.AddRuleSideEffect
import hous.release.android.presentation.our_rules.viewmodel.AddRuleViewModel
import hous.release.android.presentation.our_rules.viewmodel.MainRuleSideEffect
import hous.release.android.presentation.our_rules.viewmodel.MainRuleViewModel
import hous.release.android.presentation.practice.findActivity
import hous.release.android.util.ToastMessageUtil
import hous.release.domain.value.PhotoUri
import timber.log.Timber

@Composable
fun RuleNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = RulesScreens.Main.route,
        route = RulesScreens.RULE_GRAPH_ROUTE
    ) {
        mainRuleScreen(navController)
        addRuleScreen(navController::popBackStack)
        updateRuleScreen(navController)
    }
}

// Screens

@OptIn(ExperimentalLifecycleComposeApi::class)
private fun NavGraphBuilder.mainRuleScreen(
    navController: NavController
) {
    composable(RulesScreens.Main.route) {
        val activity = LocalContext.current.findActivity()
        val viewModel = hiltViewModel<MainRuleViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        val sideEffect = viewModel.sideEffect.collectAsStateWithLifecycle(MainRuleSideEffect.IDLE)

        var isShowLimitedAddRuleDialog by remember { mutableStateOf(false) }
        LaunchedEffect(sideEffect.value) {
            when (val event = sideEffect.value) {
                is MainRuleSideEffect.IDLE -> Unit
                is MainRuleSideEffect.ShowLimitedAddRuleDialog -> {
                    if (event.isShow) {
                        navController.navigateToAddRule()
                    } else {
                        isShowLimitedAddRuleDialog = true
                    }
                }
            }
        }
        if (isShowLimitedAddRuleDialog) {
            AddRuleLimitedDialog(onDismissRequest = {
                isShowLimitedAddRuleDialog = false
            })
        }

        MainRuleScreen(
            detailRule = uiState.value.detailRule,
            mainRules = uiState.value.filteredRules,
            searchQuery = uiState.value.searchQuery,
            fetchDetailRuleById = viewModel::fetchDetailRule,
            onSearch = viewModel::searchRule,
            onNavigateToAddRule = viewModel::canAddRule,
            onNavigateToUpdateRule = navController::navigateUpdateRule,
            onFinish = activity::finish,
            refresh = viewModel::fetchMainRules
        )
    }
}

private fun NavGraphBuilder.updateRuleScreen(
    navController: NavController
) {
    composable(
        RulesScreens.Update.route
    ) {
        navController.previousBackStackEntry?.savedStateHandle?.get<DetailRuleUiModel>(
            RulesScreens.DETAIL_RULE_KEY
        )?.let { detailRule ->
            UpdateRuleScreen(
                rule = detailRule,
                onBack = navController::popBackStack
            )
        } ?: run {
            Timber.e("DetailRuleUiModel is null")
        }
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
private fun NavGraphBuilder.addRuleScreen(onBack: () -> Unit) {
    composable(RulesScreens.Add.route) {
        val viewModel = hiltViewModel<AddRuleViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        var isOutDialogShow by remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false) }
        var isShowLimitedDialog by remember { mutableStateOf(false) }
        val sideEffect = viewModel.sideEffect.collectAsStateWithLifecycle(AddRuleSideEffect.IDLE)
        val context = LocalContext.current
        val takePhotoFromAlbumLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(5)
        ) { uriList ->
            if (uriList.isNotEmpty()) {
                viewModel.loadImage(uriList.map { PhotoUri(it.toString()) })
            }
        }
        val onOpenGallery = {
            takePhotoFromAlbumLauncher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }
        LaunchedEffect(sideEffect.value) {
            when (val event = sideEffect.value) {
                is AddRuleSideEffect.IDLE -> Unit
                is AddRuleSideEffect.DuplicateToast -> {
                    isLoading = false
                    ToastMessageUtil.showToast(
                        context,
                        context.getString(R.string.our_rule_duplicate_rule)
                    )
                }

                is AddRuleSideEffect.ShowLimitImageToast -> {
                    ToastMessageUtil.showToast(
                        context,
                        context.getString(R.string.our_rule_limit_photo_count)
                    )
                }

                is AddRuleSideEffect.LoadingBar -> {
                    isLoading = event.isLoading
                }

                is AddRuleSideEffect.PopBackStack -> {
                    isLoading = false
                    onBack()
                }

                is AddRuleSideEffect.ShowLimitRuleCountDialog -> {
                    isLoading = false
                    isOutDialogShow = true
                }
            }
        }
        val onBackPressed = {
            if (uiState.value.name.isNotBlank()) {
                isOutDialogShow = true
            } else {
                Timber.d("onBackPressed")
                onBack()
            }
        }
        BackHandler(uiState.value.name.isNotBlank(), onBackPressed)
        if (isLoading) LoadingBar()
        if (isShowLimitedDialog) {
            AddRuleLimitedDialog(onDismissRequest = { isShowLimitedDialog = false })
        }
        if (isOutDialogShow) {
            AddRuleOutDialog(
                onConfirm = {
                    isOutDialogShow = false
                    onBack()
                },
                onDismiss = {
                    isOutDialogShow = false
                }
            )
        }
        AddRuleScreen(
            ruleName = uiState.value.name,
            description = uiState.value.description,
            photos = uiState.value.photos,
            changeName = viewModel::changeName,
            changeDescription = viewModel::changeDescription,
            addRule = viewModel::addRule,
            deletePhoto = viewModel::deleteImage,
            onBack = onBackPressed,
            onOpenGallery = onOpenGallery
        )
    }
}

// Navigation

fun NavController.navigateToAddRule() {
    navigate(RulesScreens.Add.route)
}

fun NavController.navigateUpdateRule(detailRuleUiModel: DetailRuleUiModel) {
    currentBackStackEntry?.savedStateHandle?.set(
        RulesScreens.DETAIL_RULE_KEY,
        detailRuleUiModel
    )
    navigate(RulesScreens.Update.route)
}
