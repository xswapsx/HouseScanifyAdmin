package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.models.DutyDTO;
import com.appynitty.adminapp.repositories.DutyOnOffRepo;
import com.appynitty.adminapp.utils.MainUtils;

public class DutyOnOffViewModel extends ViewModel {
    private static final String TAG = "DutyOnOffViewModel";
    DutyOnOffRepo dutyOnOffRepo = DutyOnOffRepo.getInstance();
    MutableLiveData<DutyDTO> dutyDTOMutableLiveData = new MutableLiveData<>();
    private Boolean status = false;


    public void setAttendanceOn(DutyDTO reqPacket) {
        dutyOnOffRepo.turnDutyOn(reqPacket, new DutyOnOffRepo.IDutyResponse() {
            @Override
            public void onResponse(MutableLiveData<DutyDTO> dutyOnOffMutableLiveData) {
                Log.e(TAG, "onResponse: " + dutyOnOffMutableLiveData.getValue().getMessage());
                dutyDTOMutableLiveData.setValue(dutyOnOffMutableLiveData.getValue());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                DutyDTO failurePack = new DutyDTO();
                failurePack.setOnFailureMsg(t.getMessage());
                dutyDTOMutableLiveData.setValue(failurePack);
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnSwitch) {
            status = ((SwitchCompat) view).isChecked();
            if (status) {
                Log.e(TAG, "onClick: isItOn?: " + true);
                DutyDTO dataPacketOn = new DutyDTO("20.3849076", "78.1282012", "", "",
                        MainUtils.getTime(), MainUtils.getDate(), "", ""
                );
                setAttendanceOn(dataPacketOn);
            } else {
                Log.e(TAG, "onClick: isItOn?: " + false);
                DutyDTO dataPacketOff = new DutyDTO("", "", "20.3849076", "78.1282012",
                        "", "", MainUtils.getTime(), MainUtils.getDate()
                );
//                dutyDTOMutableLiveData.setValue(dataPacketOff);
                setAttendanceOn(dataPacketOff);
            }
        }
    }

    public MutableLiveData<DutyDTO> getDutyDTOMutableLiveData() {
        return dutyDTOMutableLiveData;
    }


}
