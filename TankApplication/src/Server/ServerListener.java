package Server;

import Client.Tank;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class ServerListener extends Thread {
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    Map<Integer, Tank> clients;
    ArrayList<Socket> sockets;

    public ServerListener(Socket socket, Map<Integer, Tank> clients, ArrayList<Socket> sockets) {
        super("ServerListener");
        this.socket = socket;
        this.clients = clients;
        this.sockets = sockets;
    }

    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            // Проверяем живой ли канал и работаем если живой
            while (!socket.isOutputShutdown()) {
                // Проверка на приход от клиента данных
                while (in.available() == 0)
                    Thread.sleep(1);

                String data = in.readUTF();
                String[] arrStr = data.split(" ");

                // Изменение данных об объектах
                for (Integer key : clients.keySet()) {
                    if (key == Integer.parseInt(arrStr[0])) {
                        synchronized (clients.get(key)) {
                            clients.get(key).setX(Integer.parseInt(arrStr[1]));
                            clients.get(key).setY(Integer.parseInt(arrStr[2]));
                            clients.get(key).setFilePath(arrStr[3]);
                            clients.get(key).setDir(arrStr[4]);
                            clients.get(key).setWin(arrStr[5]);
                        }
                    }
                }

                // Отправка измененных данных другому клиенту
                for (var item : sockets) {
                    if (item != socket) {
                        out = new DataOutputStream(item.getOutputStream());
                        out.writeUTF(data);
                        out.flush();
                        Thread.sleep(10);
                    }
                }
            }
        }
        catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
