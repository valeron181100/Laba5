package FileSystemStuff;

import Finances.Wallet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.util.*;

/**
 * Файловый менеджер
 */
public class FileManager {
    /**Путь к файлу*/
    private String filePath;
    /**Коллекция основная*/
    private ObservableArrayList<Wallet> collection;
    /**Дата инициализации*/
    private String initDate;
    /**Предыдущее состояние коллекции*/
    private ObservableArrayList<Wallet> previousCollectionState;

    /**
     * Конструктор
     * @param path Путь к файлу
     */
    public FileManager(String path){

        File file = new File(path);

        if(!file.exists()){
            System.err.println("Файл с коллекцией не найден.");
        }
        collection = new ObservableArrayList<>();
        previousCollectionState = new ObservableArrayList<>();
        collection.addObservableArrayListListener(commandType ->{
            saveCollection(collection);
        });
        collection.addBeforeChangingListener(commandType -> {
            previousCollectionState.clearInvisible();
            previousCollectionState.addAllInvisible(collection);
        });
        filePath = path;
        initDate = new Date().toString();
        getCollectionFromFile();
    }

    /**
     * Получает коллекцию из файла по умолчанию
     */
    public void getCollectionFromFile(){
        FileReader reader = null;
        ArrayList<Wallet> wallets = new ArrayList<>();
        try {
            reader = new FileReader(filePath);
            Scanner scanner = new Scanner(reader);
            String content = "";
            while (scanner.hasNextLine()) {
                content += scanner.nextLine();
            }

            if (content.length() == 0) {
                System.err.println("Файл с коллекцией пуст.");
            } else {
                JSONArray jsonArray;
                jsonArray = XML.toJSONObject(content).getJSONArray("array");
                jsonArray.forEach(p -> wallets.add(new Wallet((JSONObject) p)));
                int t = 0;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Файл с коллекцией не найден.");
        } catch (IOException e) {
            System.err.println("Ошибка закрытия файла с коллекцией.");
        }
        this.collection.addAllInvisible(wallets);
    }
    /**
     * Получает коллекцию из определённого файла
     */
    public void getCollectionFromFile(String filePath){
        FileReader reader = null;
        ArrayList<Wallet> wallets = new ArrayList<>();
        try {
            reader = new FileReader(filePath);
            Scanner scanner = new Scanner(reader);
            String content = "";
            while (scanner.hasNextLine()) {
                content += scanner.nextLine();
            }

            if (content.length() == 0) {
                System.err.println("Файл с коллекцией пуст.");
            } else {
                JSONArray jsonArray;
                jsonArray = XML.toJSONObject(content).getJSONArray("array");
                jsonArray.forEach(p -> wallets.add(new Wallet((JSONObject) p)));
                int t = 0;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Файл с коллекцией не найден.");
        } catch (IOException e) {
            System.err.println("Ошибка закрытия файла с коллекцией.");
        }

        this.collection.addAllInvisible(wallets);
    }

    /**
     * Возвращает предыдущее состояние коллекции
     */
    public void returnPreviousCollectionState(){
        if (previousCollectionState.size() == 0){
            System.out.println("Действия для отмены отсутствуют!");
            return;
        }

        saveCollection(previousCollectionState);
        previousCollectionState.clear();
        previousCollectionState.addAll(collection);
        collection.clearInvisible();
        getCollectionFromFile();
    }


    /**
     * Геттер коллекции
     * @return коллекцию
     */
    public ObservableArrayList<Wallet> getCollection() {
        return collection;
    }

    /**
     * Обновляет коллекцию
     */
    public void updateCollection(){
        collection.clearInvisible();
        getCollectionFromFile();
    }

    /**
     * Сохраняет коллекцию
     * @param collection коллекция для сохранения
     * @return успешность операции
     */
    public boolean saveCollection(List<Wallet> collection){
        JSONArray array = new JSONArray();
        collection.forEach(p -> array.put(p.getJson()));

        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(XML.toString(array));
            writer.close();
        } catch (IOException e) {
            System.err.println("Ошибка сохранения коллекции!");
            return false;
        }
        return true;
    }

    /**
     * Меняет путь к файлу по умолчанию
     * @param path
     */
    public void setDefaultCollectionFilePath(String path){
        filePath = path;
    }

    /**
     * Геттер даты инициализации
     * @return Дата инициализации в строчном формате
     */
    public String getInitDate() {
        return initDate;
    }
}

