//import java.util.*;
//
//public class Main {
//    public static void main(String[] args) {
//        // SET
//        System.out.println("Set: ");
//        Set<String> set = new HashSet<>();
//        // общее правило объявления ссылок на коллекции - это объявление этих ссылок с помощью интерфейса
//        // это делается для того что когда работа с этими объектами происходит в методах, то возвращаемое значение и параметр метода зачастую именно представляет собой ссылку на интерфейс.
//
//        //оператор <> позволяет не повторять параметризацию при объявлении
//
//        set.add("hey");
//        set.add("My name is");
//        boolean value = set.add("lesha");
//        System.out.println(set+ " " + value);
//        value = set.add("lesha");
//        System.out.println(set);
//        set.add(new String("lesha"));
//        System.out.println(set+ " " + value);
//        // объекты сравниваются не двойным равно,а методом Equals
//
//        Set<String> set1 = new HashSet<String>(){ // добавление в коллекцию, с помощью анонимного класса
//            { // логический блок
//                this.add("hey");
//                this.add("My name is");
//                boolean value = this.add("alex");
//            }
//        }; // но нужно добавить тип, анонимный класс не знает о типе
//        System.out.println(set1);
//
//        // List
//        List<Integer> list = new ArrayList<>();
//        list.add(5);
//        list.add(7);
//        list.add(null);
//        list.add(16);
//        list.add(99);
//        System.out.println("List: ");
//        System.out.println(list);
//        list.add(2, 23);
//        System.out.println(list);
//
//        Integer val = list.get(0);
//        System.out.println(val);
//        System.out.println(list.indexOf(99));
//        list.remove(new Integer(99));
//        System.out.println(list);
//        System.out.println(list.subList(1,3));
//
//        System.out.println("Queue:");
//        Queue<String> queue = new LinkedList<>(){
//            {
//                this.offer("Jeans");
//            }
//        };
//        queue.add("Alex");
//        queue.offer("Zanchenko");
////        for (String word: queue) {
////            System.out.println(word);
////        }
////        queue.remove();
////        queue.forEach(System.out::println);
////        queue.remove("Zanchenko");
////        queue.forEach(System.out::println);
//
////        queue.clear();
////        queue.element();
//
////        queue.removeIf(s -> s.endsWith("s"));
////        queue.forEach(System.out::println);
//        queue.stream().filter(s -> !s.endsWith("s")).forEach(System.out::println);
//        System.out.println();
//        queue.forEach(System.out::println); // Стандартные методы меняют список, стримы нет!
//
//        System.out.println("Deque");
//        Deque<Integer> stack =  new ArrayDeque<>();
//        stack.push(1);
//        stack.push(2);
//        stack.push(3);
//        while (!stack.isEmpty()) {
//            System.out.println(stack.pop()); // LIFO
//        }
//        Deque<Integer> queuee = new ArrayDeque<>();
//        queuee.offer(11);
//        queuee.offer(22);
//        queuee.offer(33);
//        while (!queuee.isEmpty()) {
//            System.out.println(queuee.poll()); //FIFO
//        }
//        // PriorityQueue
//        System.out.println("// PriorityQueue");
//        Queue<String> prior = new PriorityQueue<>(Comparator.reverseOrder());
//        prior.offer("J");
//        prior.offer("A");
//        prior.offer("V");
//        prior.offer("A");
//        prior.offer("2");
//        while(!prior.isEmpty()){
//            System.out.println(prior.poll());
//        }
//
//        // HashSet // TreeSet
//        // при создании используем HashSet
//        // при использовании использовать TreeSet т.к он экономный к памяти
//        HashSet<String> words = new HashSet<>(1000);
//        words.add("a1");
//        words.add("b1");
//        words.add("c1");
//        words.add("d1");
//        words.add("k1");
//        words.add("a1");
//        System.out.println(words);
//        TreeSet<String> treeSet = new TreeSet<>(words);
//        System.out.println(treeSet); // храниться в достаточно строгом порядке
//        for (String element: words) {
//            System.out.print(element.hashCode() + " ");
//        }
//
//        // Map
//        System.out.println("Map");
//        Map<String, Integer> map =  new HashMap<>();
//        map.put("T-Shirt",  5);
//        map.put("Jeans", 5);
//        map.put("Gloves", 5);
//        System.out.println(map);
////        System.out.println(map.put("Jeans", 3)); // замена по ключу // возвращает заменяемое значение
////        System.out.println(map);
//
//        Set<String> keySet = map.keySet();
//        System.out.println(keySet);
//        Collection<Integer> collection = map.values();
//        System.out.println(collection);
//
//        Collection<Integer> collection1 = map.values();// вывод одного элемента
//        Set<Integer> sets = new HashSet<>(collection1);
//        System.out.println(sets); ;// вывод одного элемента
//        //Collection<Integer> collection1 = map.values(); // получим все элементы
//        System.out.println(collection1);
//        Set<Map.Entry<String, Integer>> values = map.entrySet();
//        System.out.println(values);
//
//    }
//}


public class Main {
    // x is in bounds of [1;10]
    public static void main(String[] args) {

        Population population = new Population(40, true,1,10);
        for (int i = 0; i < 50; i++) {
            population.sortChromosomes();
            population.crossover();
            population.mutate();
        }
        population.sortChromosomes();
        System.out.println(population.getPopulation()[0].getValue() + " " + population.getPopulation()[0].getFitness());


        population = new Population(40, false,1,10);
        System.out.println();

        for (int i = 0; i < 50; i++) {
            population.sortChromosomes();
            population.crossover();
            population.mutate();
        }
        population.sortChromosomes();
        System.out.println(population.getPopulation()[0].getValue() + " " + population.getPopulation()[0].getFitness());
    }
}
