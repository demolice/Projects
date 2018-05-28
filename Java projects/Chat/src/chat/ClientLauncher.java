/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;
import java.net.Socket;
import static chat.Chat.PORT;

/**
 *
 * @author vaclav
 */
public class ClientLauncher {

    public static void main(String[] args) {
        System.out.println("Spouštím klient");
        try {
            //Připojí se k serveru na adrese. Zde "localhost", ale můžete zkusit i po místní síti.
            //Server zkuste spustit na jednom počítači, pomocí příkazu ipconfig zjistit jeho ip
            //(zjišťování ip si vygooglete, jestli to neznáte) a připojte se z jiného počítače.
            //PORT je definován ve třídě Chat (abychom ho mohli měnit na jednom místě)
            Socket socket = new Socket("localhost", PORT);
            System.out.println("Klient spuštěn");
            new Chat(socket); //Vytvořený Socket předá konstruktoru Chatu
        } catch (IOException e) {
            e.printStackTrace(); //Tento příkaz prostě vypíše chybu.
        }
    }
}
