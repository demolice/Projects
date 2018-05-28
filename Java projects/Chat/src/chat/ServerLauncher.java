/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.net.ServerSocket;
import java.net.Socket;
import static chat.Chat.PORT;

/**
 *
 * @author vaclav
 */
public class ServerLauncher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Spouštím server");
        try {
            //PORT je definován ve třídě Chat (abychom ho mohli měnit na jednom místě)
            ServerSocket server = new ServerSocket(PORT);
            //Vytvoření Socketu - server čeká na připojení nějakého klienta
            Socket socket = server.accept();              
            System.out.println("Server spuštěn");
            new Chat(socket); //Vytvořený Socket předá konstruktoru Chatu
        } catch (Exception e) {
            e.printStackTrace(); //Tento příkaz prostě vypíše chybu.
        }
    }
}
