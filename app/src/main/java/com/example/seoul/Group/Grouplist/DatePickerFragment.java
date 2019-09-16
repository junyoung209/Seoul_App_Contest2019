package com.example.seoul.Group.Grouplist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    EditText m_et_target;

    public DatePickerFragment(EditText target){
        m_et_target = target;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar cal = Calendar.getInstance();
        return new DatePickerDialog(getActivity(), this, cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DATE));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
        monthOfYear+=1;
        m_et_target.setText(year + "년 " + monthOfYear + "월 " + dayOfMonth + "일 ");
    }
}



