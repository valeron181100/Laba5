

public class Test {
    public static void main(String[] args){
        ObservableArrayList<String> list = new ObservableArrayList<>();
        list.addObservableArrayListListener(System.out::println);
        list.add("Hello");
        list.remove("Hello");
    }
}
