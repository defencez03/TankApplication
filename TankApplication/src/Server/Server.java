package Server;

import Client.Tank;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.*;

public class Server
{
    private int clientNum;
    private Map<Integer, Tank> clients = new HashMap<Integer, Tank>();
    private ArrayList<DataOutputStream> listOut = new ArrayList<DataOutputStream>();
    private ArrayList<DataInputStream> listIn = new ArrayList<DataInputStream>();
    private ArrayList<Socket> listSocket = new ArrayList<Socket>();

    // Запуск прослушивания подключения новых игроков
    public Server()
    {
        new ServerThread(this).start();
    }

    public void setClientNum(int clientNum) { this.clientNum = clientNum; }
    public Map<Integer, Tank> getClientInfo() { return clients; }
    public int getClientNum() { return clientNum; }
    public ArrayList<DataOutputStream> getListOut() { return listOut; }
    public ArrayList<DataInputStream> getListIn() { return listIn; }
    public ArrayList<Socket> getListSocket() { return listSocket; }
}
