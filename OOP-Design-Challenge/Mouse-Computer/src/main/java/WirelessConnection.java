public class WirelessConnection implements IConnectionType{
    private String bluetoothVersion;

    public WirelessConnection(String bluetoothVersion) {
        this.bluetoothVersion = bluetoothVersion;
    }

    public String getBluetoothVersion() {
        return bluetoothVersion;
    }

    public void setBluetoothVersion(String bluetoothVersion) {
        this.bluetoothVersion = bluetoothVersion;
    }

    @Override
    public Pair<ConnectionType, ConnectionStatus> connect() {
        return new Pair<>(ConnectionType.WIRELESS,ConnectionStatus.CONNECTED);
    }

    @Override
    public ConnectionStatus disconnect() {
        return ConnectionStatus.DISCONNECTED;
    }
}
