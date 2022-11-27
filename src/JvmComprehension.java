public class JvmComprehension {

        public static void main(String[] args) {
            int i = 1;                      // 1
            Object o = new Object();        // 2
            Integer ii = 2;                 // 3
            printAll(o, i, ii);             // 4
            System.out.println("finished"); // 7
        }

        private static void printAll(Object o, int i, Integer ii) {
            Integer uselessVar = 700;                   // 5
            System.out.println(o.toString() + i + ii);  // 6
        }
}

//JVM  видит  класс JvmComprehension просит ClassLoader загрузить класс
//Подсистема загрузчиков классов друг другу делегирует задание на загрузку Application -> Platform - > Bootstrap
//Application ClassLoader загрузит JvmComprehension
//Поскольку загрузка классов "ленивая" когда дойдет очередь Object и Integer подсистема загрузчиков классов снова
//друг другу делегирует задание на загрузку Application -> Platform - > Bootstrap
//Bootstrap ClassLoader загрузит Object и Integer
//для класса  JvmComprehension происходит Verify(подготовка на валидность)-> Prepare(подготовка примитивов - int i)
// -> Resolve(связывание ссылок - Object o = new Object(), Integer ii = 2).
//Выполняется инициализация классов, выполнятся статические инициализаторы(static void main, static void printAll).

//Классы загружаются в Metaspace
//В Stack Memory выполняется метод main()
//В Stack Memory записывается значение int i = 1;
//В heap(куче) создается объект Object;
//В Stack Memory записывается ссылка  "o" на Object;
//В heap(куче) создается объект Integer(2) ;
//В Stack Memory записывается ссылка  "ii" на Integer(2);
//В Stack Memory создается новый фрейм printAll();
//В Stack Memory записывается ссылка "o" на Object, "ii" на Integer(2). int i уже хранится в Stack Memory;
//В heap(куче) создается объект Integer(700);
//В Stack Memory,во фрейме printAll(), записывается ссылка  "uselessVar" на Integer(700);
//В Stack Memory создается новый фрейм System.out.println();
//В heap(куче) создается объект String;
//В Stack Memory, во фрейме System.out.println() записывается ссылка "o" на String,
//В Stack Memory, во фрейме System.out.println() записывается ссылка "ii" на Integer(2),
//В Stack Memory создается новый фрейм System.out.println();
//В heap(куче) создается объект String("finished");
//В Stack Memory, во фрейме System.out.println() записывается ссылка "x" на String("finished"),

//Условно если предположить, что GC сработает перед выполнением строки 8 System.out.println("finished")
//то, можно предположить что удалятся объекты: Integer(700)




