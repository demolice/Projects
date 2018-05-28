/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author vaclav
 */
public class Chat {

    public static final int PORT = 5448;
    Socket socket; //socket je proměnná pro celý objekt, aby ho mohl používat i MessageWriter

    public Chat(Socket socket) {
        //pokud neznáte obrat "this.x = x;": používá se, když mám dvě proměnné stejného jména
        //this.socket označuje proměnnou třídy
        //socket označuje proměnnou metody
        //příkaz tady dělá to, že si uložíme socket, který jsme dostali, do proměnné třídy.
        this.socket = socket;
        //Metoda start (definovaná v Thread, který MessageWriter dědí) spustí run() v novém vlákně.
        //nemůžeme přímo zavolat MessageWriter().run(); - tím bychom metodu spustili ve stejném vlákně
        //a nic dalšího bychom už dělat nemohli.
        new MessageWriter().start();
        try {
            //Vytvoříme PrintWriter, kterým můžeme "skrz" socket posílat data druhé straně.
            //druhý parametr (true) znamená, že writer se bude automaticky "flushovat"
            //(v podstatě odesílat) po každé řádce, místo toho, aby čekal na writer.flush();
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            //Tento BufferedReader čte vstup z klávesnice.
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(System.in));
            //tento cyklus se provádí souběžně s tím z MessageWriteru (řádek 68)
            while (true) {
                //přečíst řádek od uživatele
                String line = input.readLine();
                System.out.println("Posílám: " + line);
                //odeslat přes PrintWriter.
                writer.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Třída, která se stará o přijímání a vypisování zpráv druhé strany.
    private class MessageWriter extends Thread {

        @Override
        public void run() {
            try {
                //vezmeme proměnnou třídy socket a vytvoříme si z ní něco,
                //z čeho se čte příjemněji.
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String line = reader.readLine();
                
                //při odpojení se z nějakého důvodu někdy přečte "null" (jako String)
                //a někdy null (jako hodnota). Nedokážu to vysvětlit, ale v obou případech
                //chceme program ukončit.
                while (line != null && !line.equals("null")) {
                    System.out.println("Příchozí zpráva: " + line);
                    line = reader.readLine();
                }
                System.out.println("Druhá strana se odpojila.");
                System.exit(0); //Ukončí program
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}
