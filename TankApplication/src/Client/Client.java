package Client;

import java.io.*;
import java.net.Socket;


public class Client {
    private Socket socket;
    private DataOutputStream oos;
    private DataInputStream ois;
    public Area area;
    private String colorTank;

    public Client() {
        try {
            socket = new Socket("localhost", 80);
            ois = new DataInputStream(socket.getInputStream());
            oos = new DataOutputStream(socket.getOutputStream());

            if (!socket.isOutputShutdown()) {

                while (ois.available() == 0)
                    Thread.sleep(1);

                colorTank = ois.readUTF();
            }

            area = new Area(colorTank);
            area.toFront();
            new ClientListener().start();
            new ClientSender().start();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Нить на отправку данных
    private class ClientSender extends Thread {

        public ClientSender() {
           super("ClientSender");
        }

        public void run() {
            Integer numClient;
            // Проверяем живой ли канал и работаем если живой
            while (!socket.isOutputShutdown()) {
                // Проверка на совершение игроком действия
                while(!area.getEvent()) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                // Отправка данных на сервер
                try {
                    if (area.getColor().equals("Red"))
                        numClient = 0;
                    else
                        numClient = 1;

                    oos.writeUTF(numClient.toString() + " " +
                            area.getTankRed().getX().toString() + " " +
                            area.getTankRed().getY().toString() + " " +
                            area.getPath() + " " +
                            area.getTankRed().getDir() + " " +
                            area.getTankRed().getWin());
                    oos.flush();
                    Thread.sleep(10);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

                area.setEvent(false);
            }
        }
    }

    // Нить прослушивания
    private class ClientListener extends Thread {

        public ClientListener() {
            super("ClientListener");
        }

        public void run() {
            try {
                // Проверяем живой ли канал и работаем если живой
                while (!socket.isOutputShutdown()) {
                    // Проверка на приход от сервера данных
                    while (ois.available() == 0)
                        Thread.sleep(1);

                    String data = ois.readUTF();
                    String[] arrStr = data.split(" ");

                    // Изменение данных об объектах
                    area.getTankBlue().setX(Integer.parseInt(arrStr[1]));
                    area.getTankBlue().setY(Integer.parseInt(arrStr[2]));
                    area.getTankBlue().setFilePath(arrStr[3]);
                    area.getTankBlue().setDir(arrStr[4]);
                    area.getTankBlue().setWin(arrStr[5]);
                    area.repaint();

                }

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
