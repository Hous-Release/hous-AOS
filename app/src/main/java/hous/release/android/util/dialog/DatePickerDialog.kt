package hous.release.android.util.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import hous.release.android.R
import hous.release.android.databinding.DialogDatePickerBinding
import hous.release.android.util.dialog.WarningDialogFragment.Companion.CONFIRM_ACTION
import hous.release.android.util.extension.initLayout
import timber.log.Timber
import java.util.*

class DatePickerDialog : DialogFragment() {
    private var _binding: DialogDatePickerBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogDatePickerBinding.inflate(requireActivity().layoutInflater)
        initDatePicker()
        initConfirmTextClickListener()
        initCancelTextClickListener()
        return activity?.let {
            val dialog = AlertDialog.Builder(it).create()
            dialog.setView(binding.root)
            dialog
        } ?: throw IllegalStateException()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initDatePicker() {
        val year = arguments?.getParcelable<DatePickerClickListener>(CONFIRM_ACTION)!!.birtyYear
        val month = arguments?.getParcelable<DatePickerClickListener>(CONFIRM_ACTION)!!.birthMonth
        val day = arguments?.getParcelable<DatePickerClickListener>(CONFIRM_ACTION)!!.birthDay
        Timber.e("$year, $month $day")
        with(binding.datePickerDialogDatePicker) {
            maxDate = Calendar.getInstance().timeInMillis
            /* 구현 안 되는 부분
            if (year == -1) {
                Calendar.getInstance().timeInMillis
            } else {
                Calendar.getInstance().set(year, month, day)
            }
            */
        }
    }

    private fun getYearFormat(year: Int): String = year.toString()

    private fun getMonthFormat(month: Int): String = "%02d".format(month + 1)

    private fun getDayFormat(day: Int): String = "%02d".format(day)

    private fun initConfirmTextClickListener() {
        binding.tvDatePickerConfirm.setOnClickListener {
            with(binding.datePickerDialogDatePicker) {
                val yearFormat = getYearFormat(year)
                val monthFormat = getMonthFormat(month)
                val dayFormat = getDayFormat(dayOfMonth)
                val date = "$yearFormat-$monthFormat-$dayFormat"
                arguments?.getParcelable<DatePickerClickListener>(CONFIRM_ACTION)
                    ?.onConfirmClick(date)
                    ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
            }
            dismiss()
        }
    }

    private fun initCancelTextClickListener() {
        binding.tvDatePickerCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
