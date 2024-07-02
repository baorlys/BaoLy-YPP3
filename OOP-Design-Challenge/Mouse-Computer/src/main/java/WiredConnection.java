public class WiredConnection implements IConnectionType{
    private String cableType;

    public WiredConnection(String cableType) {
        this.cableType = cableType;
    }

    public String getCableType() {
        return cableType;
    }

    public void setCableType(String cableType) {
        this.cableType = cableType;
    }

    @Override
    public Pair<ConnectionType, ConnectionStatus> connect() {
        return new Pair<>(ConnectionType.WIRED,ConnectionStatus.CONNECTED);
    }

    @Override
    public ConnectionStatus disconnect() {
        return ConnectionStatus.DISCONNECTED;
    }
}
