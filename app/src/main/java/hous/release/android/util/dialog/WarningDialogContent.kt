package hous.release.android.util.dialog

import android.content.Context
import hous.release.android.R

data class WarningDialogContent(
    val title: String = "",
    val desc: String = "",
    val cancel: String = "",
    val confirm: String = ""
) {
    fun getWarningSplash(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_splash_title),
            desc = context.getString(R.string.warning_splash_desc),
            cancel = context.getString(R.string.warning_splash_cancel),
            confirm = context.getString(R.string.warning_splash_confirm)
        )

    fun getWarningEditHousName(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_edit_hous_name_title),
            desc = context.getString(R.string.warning_edit_hous_name_desc),
            cancel = context.getString(R.string.warning_edit_hous_name_cancel),
            confirm = context.getString(R.string.warning_edit_hous_name_confirm)
        )

    fun getWarningAddRule(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_add_rule_title),
            desc = context.getString(R.string.warning_add_rule_desc),
            cancel = context.getString(R.string.warning_add_rule_cancel),
            confirm = context.getString(R.string.warning_add_rule_confirm)
        )

    fun getWarningEditRule(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_edit_rule_title),
            desc = context.getString(R.string.warning_edit_rule_desc),
            cancel = context.getString(R.string.warning_edit_rule_cancel),
            confirm = context.getString(R.string.warning_edit_rule_confirm)
        )

    fun getWarningDeleteRule(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_delete_rule_title),
            desc = context.getString(R.string.warning_delete_rule_desc),
            cancel = context.getString(R.string.warning_delete_rule_cancel),
            confirm = context.getString(R.string.warning_delete_rule_confirm)
        )

    fun getWarningAddToDo(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_add_to_do_title),
            desc = context.getString(R.string.warning_add_to_do_desc),
            cancel = context.getString(R.string.warning_add_to_do_cancel),
            confirm = context.getString(R.string.warning_add_to_do_confirm)
        )

    fun getWarningEditToDo(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_edit_to_do_title),
            desc = context.getString(R.string.warning_edit_to_do_desc),
            cancel = context.getString(R.string.warning_edit_to_do_cancel),
            confirm = context.getString(R.string.warning_edit_to_do_confirm)
        )

    fun getWarningDeleteToDo(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_delete_to_do_title),
            desc = context.getString(R.string.warning_delete_to_do_desc),
            cancel = context.getString(R.string.warning_delete_to_do_cancel),
            confirm = context.getString(R.string.warning_delete_to_do_confirm)
        )

    fun getWarningEditProfile(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_edit_profile_title),
            desc = context.getString(R.string.warning_edit_profile_desc),
            cancel = context.getString(R.string.warning_edit_profile_cancel),
            confirm = context.getString(R.string.warning_edit_profile_confirm)
        )

    fun getWarningLogout(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_logout_title),
            desc = context.getString(R.string.warning_logout_desc),
            cancel = context.getString(R.string.warning_logout_cancel),
            confirm = context.getString(R.string.warning_logout_confirm)
        )

    fun getWarningStopTest(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_stop_test_title),
            desc = context.getString(R.string.warning_stop_test_desc),
            cancel = context.getString(R.string.warning_stop_test_cancel),
            confirm = context.getString(R.string.warning_stop_test_confirm)
        )
    fun getWarningFeedBack(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.waring_peed_back_title),
            desc = context.getString(R.string.waring_peed_back_desc),
            cancel = context.getString(R.string.waring_peed_back_cancel),
            confirm = context.getString(R.string.waring_peed_back_confirm)
        )
}
