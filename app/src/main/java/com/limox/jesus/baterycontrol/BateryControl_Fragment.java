package com.limox.jesus.baterycontrol;


import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class BateryControl_Fragment extends Fragment {


    private ProgressBar mPgLevel;
    private TextView mTxvLevel;
    private ImageView mIvStatus;

    public BateryControl_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_batery_control, container, false);
        mPgLevel = (ProgressBar) rootView.findViewById(R.id.pgLevel);
        mTxvLevel = (TextView) rootView.findViewById(R.id.txvLevel);
        mIvStatus = (ImageView) rootView.findViewById(R.id.ivStatus);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Crear un intent filter para el action Intent.AACTION_BATERY_CHANGED
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        //Registrar en primer ligar el BroadcasReceiber
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    /**
     * Broadcast que depende del ciclo de vida del Fragment
     */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        /**
         * Lee la informacion que llega del intent: Level y status
         */
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int sclae = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float batteryPct = level / (float) sclae;
            mPgLevel.setProgress(level);

            //Esto de la bateria
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            switch (status){
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    mIvStatus.setImageResource(R.mipmap.ic_chargin);
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    mIvStatus.setImageResource(R.mipmap.ic_uncharged);
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    mIvStatus.setImageResource(R.mipmap.ic_full);
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    mIvStatus.setImageResource(R.mipmap.ic_unknown);
                    break;
               /* case BatteryManager.BATTERY_STATUS_:
                    mIvStatus.setImageResource(R.mipmap.ic_chargin);
                    break;*/
            }
        }
    };

}
