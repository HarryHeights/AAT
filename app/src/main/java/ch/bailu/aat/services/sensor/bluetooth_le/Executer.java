package ch.bailu.aat.services.sensor.bluetooth_le;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.support.annotation.RequiresApi;

import java.util.Stack;
import java.util.UUID;

@RequiresApi(api = 18)
public class Executer {
    private final static UUID ENABLE_NOTIFICATION = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");


    private final Stack<BluetoothGattCharacteristic> toRead = new Stack<>();
    private final Stack<BluetoothGattCharacteristic> toNotify = new Stack<>();

    private boolean discoverd = false;


    public void notify(BluetoothGattCharacteristic c) {
        toNotify.push(c);
    }
    public void read(BluetoothGattCharacteristic c) {
        toRead.push(c);
    }

    public boolean needToDiscover() { return !discoverd; }
    public boolean haveToRead() {
        return toRead.size() > 0;
    }
    public boolean haveToNotify() { return toNotify.size() > 0;}



    public void next(BluetoothGatt gatt) {
        if (needToDiscover()) {
            discoverd = gatt.discoverServices();

        } else if (haveToRead()) {
            gatt.readCharacteristic(toRead.pop());

        } else if (haveToNotify()) {
            enableNotification(gatt, toNotify.pop());

        }
    }


    private void enableNotification(BluetoothGatt gatt, BluetoothGattCharacteristic c) {

        BluetoothGattDescriptor d = c.getDescriptor(ENABLE_NOTIFICATION);

        if (d != null) {
            gatt.setCharacteristicNotification(c, true);
            d.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            gatt.writeDescriptor(d);
        }
    }
}
