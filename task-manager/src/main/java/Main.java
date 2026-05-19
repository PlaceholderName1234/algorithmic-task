import service.TaskManagerService;

public class Main {

    public static void main(String[] args) {
        System.out.println("Многопоточная система обработки заказов");
        System.out.println("=======================================");
        System.out.println();

        int minOrders = 15; // создастся не менее стольки заказов (может и больше)

        TaskManagerService service = new TaskManagerService();

        service.start();
        service.waitForCompletion(minOrders);
        service.stop();
    }
}