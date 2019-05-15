package FileSystemStuff;

import Finances.Wallet;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс-комманда
 */
public class Command {
    /**
     * Тип комманды
     */
    CommandType type;
    /**
     * Файловый менеджер
     */
    private FileManager manager;

    /**
     * аргументы комманды
     */
    private String[] args;

    /**
     * Конструктор
     * @param manager Файловый менеджер
     */
    public Command(FileManager manager){
        this.manager = manager;
        type = null;
        args = new String[2];
    }

    /**
     * парсер комманд
     * @param input комманда в формате json
     */
    public void parse(String input){
        String insertRegex = "insert \\{\\d+} \\{\\{\"currencyType\":\".+\",\"amount\":\\d+}}";
        String oneArgCmdRegex = "(remove_greater|add|remove) \\{\\{\"currencyType\":\".+\",\"amount\":\\d+}}";
        String noArgCmdRegex = "show|remove_last|info|help|start|exit|undo";
        String jsonRegex = "\\{\"currencyType\":\".+\",\"amount\":\\d+}";
        if(input.matches(insertRegex)){
            type = CommandType.valueOf(input.split(" ")[0].toUpperCase());
            String almostFirstArg = findMatches("\\d+", input).get(0);
            String almostSecondArg = findMatches(jsonRegex, input).get(0);
            args[0] = almostFirstArg;
            args[1] = almostSecondArg;
        }
        else if (input.matches(oneArgCmdRegex)){
            type = CommandType.valueOf(input.split(" ")[0].toUpperCase());
            String almostFirstArg = findMatches(jsonRegex, input).get(0);
            args[0] = almostFirstArg;
        }
        else if(input.matches(noArgCmdRegex)){
            type = CommandType.valueOf(input.toUpperCase());
        }
        else{
            type = null;
        }
    }

    //Commands

    /**
     * Удаляет все элементы, которые больше данного
     * @param manager Файловый менеджер с коллекцией
     * @return если этот метод, не метод start, то false, иначе - true
     */
    public boolean removeGreater(FileManager manager){
        manager.updateCollection();
        try {
            Wallet element = new Wallet(new JSONObject(args[0]));
            ArrayList<Wallet> wallets = new ArrayList<>();
            for (int i = 0; i < manager.getCollection().size(); i++) {
                Wallet wallet = manager.getCollection().get(i);
                if(wallet.checkMoney().getAmount() > element.checkMoney().getAmount())
                    wallets.add(wallet);
            }
            manager.getCollection().removeAll(wallets);
            System.out.println("Команда выполнена!");
        }
        catch (JSONException e){
            System.err.println("Неверный ввод аргумента команды!");
        }
        return false;
    }
    /**
     *Добавляет элемент в коллекцию
     * @param manager Файловый менеджер с коллекцией
     * @return если этот метод, не метод start, то false, иначе - true
     */
    public boolean addElement(FileManager manager){
        manager.updateCollection();
        try {
            Wallet element = new Wallet(new JSONObject(args[0]));
            manager.getCollection().add(element);
            System.out.println("Команда выполнена!");
        }
        catch (JSONException e){
            System.err.println("Неверный ввод аргумента команды!");
        }
        return false;
    }
    /**
     *Удаляет элемент из коллекции
     * @param manager Файловый менеджер с коллекцией
     * @return если этот метод, не метод start, то false, иначе - true
     */
    public boolean removeElement(FileManager manager){
        manager.updateCollection();
        try {
            Wallet element = new Wallet(new JSONObject(args[0]));
            manager.getCollection().remove(element);
            System.out.println("Команда выполнена!");
        }
        catch (JSONException e){
            System.err.println("Неверный ввод аргумента команды!");
        }
        return false;
    }
    /**
     * Удаляет последний элемент в коллекции
     * @param manager Файловый менеджер с коллекцией
     * @return если этот метод, не метод start, то false, иначе - true
     */
    public boolean removeLast(FileManager manager){
        manager.updateCollection();
        manager.getCollection().remove(manager.getCollection().size() - 1);
        System.out.println("Команда выполнена!");
        return false;
    }
    /**
     *  Выводит на экран информацию о коллекции
     * @param manager Файловый менеджер с коллекцией
     * @return если этот метод, не метод start, то false, иначе - true
     */
    public boolean info(FileManager manager) {
        manager.updateCollection();
        try(ByteArrayOutputStream byteObject = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteObject)) {
            objectOutputStream.writeObject(manager.getCollection().toArray());
            System.out.println(String.format(
                    "Тип коллекции: %s \nТип элементов коллекции: %s\nДата инициализации: %s\nКоличество элементов: %s\nРазмер: %s байт\n",
                    manager.getCollection().getClass().getName(),
                    "Finances.Wallet", manager.getInitDate(), manager.getCollection().size(), byteObject.toByteArray().length
            ));
            System.out.println("Команда выполнена!");
        } catch (IOException e) {
            //System.err.println("Ошибка при определении размера памяти коллекции.");
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Вставляет в коллекцию элемент по индексу
     * @param manager Файловый менеджер с коллекцией
     * @return если этот метод, не метод start, то false, иначе - true
     */
    public boolean insert(FileManager manager){
        manager.updateCollection();
        try {
            if(Integer.parseInt(args[0]) >= manager.getCollection().size()){
                System.out.println("Ошибка: слишком большой индекс!");
                return false;
            }
            Wallet element = new Wallet(new JSONObject(args[1]));
            manager.getCollection().add(Integer.parseInt(args[0]), element);
            System.out.println("Команда выполнена!");
        }
        catch (JSONException e){
            System.err.println("Неверный ввод аргумента команды!");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Слишком большой индекс");
        }
        return false;
    }
    /**
     *  Выводит на экран все элементы коллекции
     * @param manager Файловый менеджер с коллекцией
     * @return если этот метод, не метод start, то false, иначе - true
     */
    public boolean show(FileManager manager){
        manager.updateCollection();
        manager.getCollection().forEach(p -> System.out.println(p.toString() + "\t"));
        System.out.println("Команда выполнена!");
        return false;
    }
    /**
     * Выводит на экран справку
     * @param manager Файловый менеджер с коллекцией
     * @return если этот метод, не метод start, то false, иначе - true
     */
    public boolean help(FileManager manager){
        System.out.println("remove_greater {element}: удалить из коллекции все элементы, превышающие заданный\n" +
                "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element}: добавить новый элемент в коллекцию\n" +
                "remove_last: удалить последний элемент из коллекции\n" +
                "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "remove {element}: удалить элемент из коллекции по его значению\n" +
                "insert {int index} {element}: добавить новый элемент в заданную позицию\n" +
                "help: справка по командам\n" +
                "start: запуск подпрограммы\n" +
                "undo: отменить последнюю команду\n" +
                "exit: выход\n" +
                "Формат json объекта:\n" +
                "{\"currencyType\":\"Finances.Ruble\",\"amount\":200}");
        return false;
    }
    /**
     * Завершает работ программы
     * @return если этот метод, не метод start, то false, иначе - true
     */
    public boolean exit(){
        System.out.println("Завершение работы программы!");
        System.exit(0);
        return false;
    }
    /**
     * Начинает работу подпрограммы
     * @param manager Файловый менеджер с коллекцией
     * @return если этот метод, не метод start, то false, иначе - true
     */
    public boolean start(FileManager manager){
        manager.updateCollection();
        if (manager.getCollection().size() < 3) {
            System.out.println("Слишком мало элементов в коллекции для запуска программы!");
            return false;
        }
        return true;
    }
    /**
     * Отменяет последнюю введенную операцю
     * @param manager Файловый менеджер с коллекцией
     * @return если этот метод, не метод start, то false, иначе - true
     */
    public boolean undo(FileManager manager){
        manager.updateCollection();
        manager.returnPreviousCollectionState();
        System.out.println("Команда выполнена!");
        return false;
    }

    /**
     * Метод определяющий какую комманду вызвать
     * @return если эта комманда, не start, то false, иначе - true
     */
    public boolean run(){
        if (type != null){
            if (type == CommandType.EXIT || type == CommandType.HELP || manager.getCollection().size() != 0)
                switch (type){
                case ADD: type = null; return addElement(manager);
                case HELP: type = null; return help(manager);
                case INFO: type = null; return info(manager);
                case SHOW: type = null; return show(manager);
                case START: type = null; return start(manager);
                case INSERT: type = null; return insert(manager);
                case REMOVE: type = null; return removeElement(manager);
                case REMOVE_LAST: type = null; return removeLast(manager);
                case REMOVE_GREATER: type = null; return  removeGreater(manager);
                case EXIT: type = null; return exit();
                    case UNDO: type = null; return undo(manager);
                default: type = null; return false;
            }
            else
                System.err.println("Файл с коллекцией пуст");
            return false;
        }
        else {
            System.err.println("Неверная команда или команда была не введена");
            return false;
        }

    }

    /**
     * Геттер файлового менеджера
     * @return файловый менеджер
     */
    public FileManager getFileManager() {
        return manager;
    }

    /**
     * Находит все совпадения в тексте
     * @param patterStr регулярной выражение для поиска
     * @param text текст в котором надо искать
     * @return
     */
    private static ArrayList<String> findMatches(String patterStr, String text){
        Pattern pattern = Pattern.compile(patterStr);
        Matcher matcher = pattern.matcher(text);
        ArrayList<String> collection = new ArrayList<>();
        while(matcher.find()){
            collection.add(text.substring(matcher.start(), matcher.end()));
        }
        return collection;
    }
}
