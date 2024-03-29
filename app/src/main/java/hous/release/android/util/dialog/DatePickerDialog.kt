package hous.release.android.util.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import hous.release.android.R
import hous.release.android.databinding.DialogDatePickerBinding
import hous.release.android.util.dialog.WarningDialogFragment.Companion.CONFIRM_ACTION
import hous.release.android.util.extension.initLayout
import hous.release.android.util.extension.parcelable
import timber.log.Timber
import java.util.Calendar

class DatePickerDialog : DialogFragment() {
    private var _binding: DialogDatePickerBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogDatePickerBinding.inflate(requireActivity().layoutInflater)
        initDatePicker()
        initConfirmTextClickListener()
        initCancelTextClickListener()
        initMaxDate()
        isCancelable = false
        return activity?.let {
            val dialog = MaterialAlertDialogBuilder(
                requireActivity(),
                R.style.MaterialAlertDialog_rounded
            ).create()
            dialog.setView(binding.root)
            dialog
        } ?: throw IllegalStateException()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initMaxDate() {
        binding.datePickerDialogDatePicker.maxDate = Calendar.getInstance().timeInMillis
    }

    private fun initDatePicker() {
        val (year, month, day) = arguments?.getString(USER_BIRTHDAY)?.let {
            if (it.isBlank()) return@let null
            val date = it.split("-")
            Triple(date[0].toInt(), date[1].toInt(), date[2].toInt())
        } ?: Triple(
            Calendar.getInstance()[Calendar.YEAR],
            Calendar.getInstance()[Calendar.MONTH] + 1,
            Calendar.getInstance()[Calendar.DAY_OF_MONTH]
        )
        binding.datePickerDialogDatePicker.init(year, month - 1, day, null)
    }

    private fun initConfirmTextClickListener() {
        binding.tvDatePickerConfirm.setOnClickListener {
            with(binding.datePickerDialogDatePicker) {
                val yearFormat = getYearFormat(year)
                val monthFormat = getMonthFormat(month)
                val dayFormat = getDayFormat(dayOfMonth)
                val date = "$yearFormat-$monthFormat-$dayFormat"
                arguments?.parcelable<DatePickerClickListener>(CONFIRM_ACTION)
                    ?.onConfirmClick(date)
                    ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
                updateDate(year, month, dayOfMonth)
            }
            dismiss()
        }
        binding.datePickerDialogDatePicker.setOnDateChangedListener { _, year, month, dayOfMonth ->
            Timber.e("year: $year, month: $month, dayOfMonth: $dayOfMonth")
        }
    }

    private fun initCancelTextClickListener() {
        binding.tvDatePickerCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun getYearFormat(year: Int): String = year.toString()

    private fun getMonthFormat(month: Int): String = "%02d".format(month + 1)

    private fun getDayFormat(day: Int): String = "%02d".format(day)
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val USER_BIRTHDAY = "birthDAY"
    }
}
