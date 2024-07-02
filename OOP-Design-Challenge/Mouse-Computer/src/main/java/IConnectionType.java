public interface IConnectionType {
    Pair<ConnectionType, ConnectionStatus> connect();
    ConnectionStatus disconnect();
}
