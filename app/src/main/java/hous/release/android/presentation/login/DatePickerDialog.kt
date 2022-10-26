package hous.release.android.presentation.login

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import hous.release.android.R
import hous.release.android.databinding.DialogDatePickerBinding
import hous.release.android.util.extension.initLayout
import java.lang.IllegalStateException

class DatePickerDialog(private val doAfterConfirm: (date: String) -> Unit) :
    DialogFragment() {
    private var _binding: DialogDatePickerBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogDatePickerBinding.inflate(requireActivity().layoutInflater)
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

    private fun sendSelectedDate() {
        with(binding.datePickerDialogDatePicker) {
            val yearFormat = getYearFormat(year)
            val monthFormat = getMonthFormat(month)
            val dayFormat = getDayFormat(dayOfMonth)
            val date = "$yearFormat-$monthFormat-$dayFormat"
            doAfterConfirm(date)
        }
        dismiss()
    }

    private fun getYearFormat(year: Int): String = year.toString()

    private fun getMonthFormat(month: Int): String {
        return if (month < 9) {
            "0" + (month + 1).toString()
        } else {
            (month + 1).toString()
        }
    }

    private fun getDayFormat(day: Int): String {
        return if (day < 10) {
            "0$day"
        } else {
            day.toString()
        }
    }

    private fun initConfirmTextClickListener() {
        binding.tvDatePickerConfirm.setOnClickListener {
            sendSelectedDate()
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
