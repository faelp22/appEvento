package com.example.droid02.appevento.receiver;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.droid02.appevento.dao.ParticipanteDAO;

/**
 * Created by droid02 on 09/07/16.
 */
public class SMSReceiver extends BroadcastReceiver {
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];

        SmsMessage sms = null;
        String formato = (String) intent.getSerializableExtra("format");
        sms = SmsMessage.createFromPdu(pdu, formato);

        String telefone = sms.getDisplayOriginatingAddress();

        ParticipanteDAO dao = new ParticipanteDAO(context);

        if (dao.ehParticipante(telefone)) {
            Toast.makeText(context, "Chegou SMS de Participante", Toast.LENGTH_LONG).show();
        }

        dao.close();
    }
}
