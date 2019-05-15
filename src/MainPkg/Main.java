package MainPkg;

import Buildings.*;
import Entities.*;
import Enums.*;
import FileSystemStuff.Command;
import FileSystemStuff.FileManager;
import FileSystemStuff.ObservableArrayList;
import Finances.*;
import Mind.*;
import Talks.*;

import javax.sound.midi.SysexMessage;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public class Lab4Program{
       public void start(ObservableArrayList<Wallet> wallets){
            class Neznaika extends Human{
                public Neznaika(String name, Color eyeColor, int legsNum, int armsNum, int headNum, boolean isMan, int years, Color hairColor, BodyType bodyType, Wallet wallet) {
                    super(name, eyeColor, legsNum, armsNum, headNum, isMan, years, hairColor, bodyType, wallet);
                }
            }

            Neznaika neznaika = new Neznaika("Незнайка", Color.Blue, 2, 2, 1, true,
                    15, Color.Blonde, BodyType.Athletic, wallets.get(0));
            Human seller = new Human("Люба", Color.Green, 2, 2, 1, false,
                    25, Color.Red, BodyType.Plump, wallets.get(1));
            Human owner = new Human("Гриша", Color.Blue, 2, 2, 1, true,
                    45, Color.Black, BodyType.Fat, wallets.get(2));
            Animal kozel = new Animal("Козёл", Color.Gray, 4,0,1,true,
                    9, true, Color.White);

            //Anonymous class for 4 Lab
            PaperMessage paperMessage = new PaperMessage() {

                private String message;
                private String sender;
                private String receiver;

                @Override
                public String getMessage() {
                    return message;
                }

                @Override
                public String getSender() {
                    return sender;
                }

                @Override
                public String getReceiver() {
                    return receiver;
                }

                @Override
                public void create(String sender, String receiver, String msg) {
                    if(message == null || sender == null || receiver == null){
                        this.sender = sender;
                        this.receiver = receiver;
                        message = msg;
                    }
                }
            };

            paperMessage.create("Zhulio", "None", "This text from paperMessage and somebody is reading it now!");

            neznaika.getPocketContent().add(paperMessage);
            //////////////
            Street street = new Street("Невский проспект", new Building(BuildingType.Dwelling, 15),
                    new Building(BuildingType.Hospital, 6), new Building(BuildingType.School, 10),
                    new Building(BuildingType.Shop,3), new Building(BuildingType.University, 14));

            street.addEntities(neznaika, seller, owner, kozel);

            seller.getPosition().setBuilding(street.getStreetBuildings().get(3));

            for(int i = 0; i < street.getStreetBuildings().size(); i++){
                neznaika.goForward();
                neznaika.exploreStreet();
                kozel.goForward();
                kozel.exploreStreet();
            }

            for(int i = street.getStreetBuildings().size()-1; i != 0 ; i--){
                neznaika.goBack();
                neznaika.exploreStreet();
                kozel.goBack();
                kozel.exploreStreet();
            }
            for(int i = 0; i < 3; i++){
                neznaika.goForward();
                neznaika.exploreStreet();
                kozel.goForward();
                kozel.exploreStreet();
            }



            Talk talk = new Talk(street, null, neznaika, kozel);
            String sentence1 = kozel.recallSmth().generateSentence(SentenceType.Question, null);
            talk.say(kozel, sentence1);


            talk.say(neznaika, neznaika.recallSmth().generateSentence(SentenceType.Positive, sentence1));
            talk.end();

            neznaika.getPosition().setBuilding(street.getStreetBuildings().get(3));

            kozel.getPosition().setBuilding(street.getStreetBuildings().get(3));

            Talk talk1 = new Talk(street, street.getStreetBuildings().get(3), neznaika, seller);

            String sentence2 = kozel.recallSmth().generateSentence(SentenceType.Question, null);
            talk1.say(neznaika, sentence2);
            Thought thought = seller.recallSmth();
            if(thought != null)
                talk1.say(seller, seller.recallSmth().generateSentence(SentenceType.Positive, sentence2));
            else
                talk1.say(seller, "Я не знаю.");
            talk1.end();

            neznaika.getPosition().setBuilding(null);
            kozel.getPosition().setBuilding(null);

            owner.playLottery();

            if(owner.getWallet().checkMoney().getAmount() > 700000){
                owner.getWallet().giveMoney(seller, new Ruble(100000));
            }

            Talk monolog = new Talk(street, neznaika.getPosition().getBuilding(), neznaika);

            monolog.say(neznaika, neznaika.getPocketContent().get(0).getMessage());

            Main.pause("Конец!");

        }
    }

    public static class PauseClass{
        public void pauseProgram(){
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        String filePath = System.getenv("FILE");
        if(filePath == null){
            System.out.println("Введите путь к файлу через переменную окружения 'FILE'");
            System.exit(0);
        }
        FileManager manager = new FileManager("file.xml");
        Command command = new Command(manager);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите help для получения справки");
        while(true){
            String line = "";

            try{
                line = scanner.nextLine();
            }
            catch (NoSuchElementException e){
                System.err.println("Экстренное завершение работы программы");
                System.exit(0);
            }

            command.parse(line);
            if (command.run()){
                new Main().new Lab4Program().start(manager.getCollection());
            }
        }
    }

    public static void pause(String message){
        System.out.println(message);
        new Main.PauseClass().pauseProgram();
    }
}

//При добавлении новой команды:
//1. Добавить команду в ObservableArrayListAction
//2. Отредактировать Command.parse()
//3. Добавить соответствующий команде метод в класс Command
//4. Прописать запуск комманды в switch-конструкции метода Command.run()