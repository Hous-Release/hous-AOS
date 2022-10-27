package hous.release.android.presentation.todo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.DialogToDoBottomSheetBinding
import hous.release.android.presentation.todo.detail.daily.DailyFragment.Companion.TODO_ID
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.dialog.WarningDialogFragment
import hous.release.android.util.dialog.WarningType
import hous.release.domain.entity.TodoDetail
import hous.release.domain.usecase.GetTodoDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TodoBottomSheet : BottomSheetDialogFragment() {
    @Inject
    lateinit var getTodoDetailUseCase: GetTodoDetailUseCase
    private lateinit var participantAdapter: TodoParticipantAdapter
    private var todoId: Int = 0
    private var _binding: DialogToDoBottomSheetBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))
    private val _todoDetailContent: MutableStateFlow<TodoDetail> = MutableStateFlow(
        TodoDetail(
            name = "",
            selectedUsers = emptyList(),
            dayOfWeeks = ""
        )
    )
    private val todoDetailContent: StateFlow<TodoDetail> = _todoDetailContent.asStateFlow()
    private val todoDetailViewModel: TodoDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        todoId = arguments?.getInt(TODO_ID) ?: 0
        fetchTodoDetailContent()
        participantAdapter = TodoParticipantAdapter()
        _binding = DialogToDoBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEditButtonOnClickListener()
        initDeleteButtonOnClickListener()
        bindingTodoDetailContent()
    }

    private fun fetchTodoDetailContent() {
        viewLifecycleOwner.lifecycleScope.launch {
            getTodoDetailUseCase(todoId)
                .onSuccess { todoDetail ->
                    _todoDetailContent.value = _todoDetailContent.value.copy(
                        name = todoDetail.name,
                        selectedUsers = todoDetail.selectedUsers,
                        dayOfWeeks = todoDetail.dayOfWeeks
                    )
                }
                .onFailure { Timber.e(it.message) }
        }
    }

    private fun bindingTodoDetailContent() {
        binding.rvToDoParticipants.adapter = participantAdapter
        todoDetailContent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { todoDetail ->
                with(binding) {
                    tvToDoTitle.text = todoDetail.name
                    tvToDoDays.text = todoDetail.dayOfWeeks
                }
                participantAdapter.submitList(todoDetail.selectedUsers)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initEditButtonOnClickListener() {
        binding.tvToDoEdit.setOnClickListener {
            /* todo 수정하기 뷰로 이동 */
        }
    }

    private fun initDeleteButtonOnClickListener() {
        binding.tvToDoDelete.setOnClickListener {
            WarningDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(
                        WarningDialogFragment.WARNING_TYPE,
                        WarningType.WARNING_DELETE_TO_DO
                    )
                    putParcelable(
                        WarningDialogFragment.CONFIRM_ACTION,
                        ConfirmClickListener(
                            id = todoId,
                            confirmActionWithId = { todoId ->
                                todoDetailViewModel.deleteTodo(todoId)
                                this@TodoBottomSheet.dismiss()
                            }
                        )
                    )
                }
            }.show(childFragmentManager, WarningDialogFragment.DIALOG_WARNING)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
