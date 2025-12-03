package mycode.onlineshopspring.view;

import mycode.onlineshopspring.customer.dto.CustomerDto;
import mycode.onlineshopspring.customer.dto.CustomerListResponse;
import mycode.onlineshopspring.customer.dto.CustomerResponse;
import mycode.onlineshopspring.customer.service.UserCommandService;
import mycode.onlineshopspring.customer.service.UserQuerryService;
import mycode.onlineshopspring.exceptions.CustomerDoesntExistException;
import mycode.onlineshopspring.exceptions.ProductDoesntExistException;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsDto;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsListResponse;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsResponse;
import mycode.onlineshopspring.orderDetails.service.OrderDetailsCommandService;
import mycode.onlineshopspring.orderDetails.service.OrderDetailsQuerryService;
import mycode.onlineshopspring.orders.dto.OrdersDto;
import mycode.onlineshopspring.orders.dto.OrdersListResponse;
import mycode.onlineshopspring.orders.dto.OrdersResponse;
import mycode.onlineshopspring.orders.service.OrdersCommandService;
import mycode.onlineshopspring.orders.service.OrdersQuerryService;
import mycode.onlineshopspring.products.dto.ProductsDto;
import mycode.onlineshopspring.products.dto.ProductsListResponse;
import mycode.onlineshopspring.products.dto.ProductsResponse;
import mycode.onlineshopspring.products.service.ProductsCommandService;
import mycode.onlineshopspring.products.service.ProductsQuerryService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

@Component
public class View {

    private final UserQuerryService userQuerryService;
    private final UserCommandService userCommandService;
    private final ProductsQuerryService productsQuerryService;
    private final OrdersQuerryService ordersQuerryService;
    private final OrderDetailsQuerryService orderDetailsQuerryService;
    private final ProductsCommandService productsCommandService;
    private final OrdersCommandService ordersCommandService;
    private final OrderDetailsCommandService orderDetailsCommandService;
    private final Scanner scanner;
    private final Map<Integer, MenuAction> menuActions = new LinkedHashMap<>();

    private enum MenuCategory { READ, CREATE }

    private record MenuAction(String description, Runnable handler, MenuCategory category) { }
    private record PaginationInput(int page, int size) { }

    public View(UserCommandService userCommandService,
                UserQuerryService userQuerryService,
                ProductsQuerryService productsQuerryService,
                OrdersQuerryService ordersQuerryService,
                OrderDetailsQuerryService orderDetailsQuerryService,
                ProductsCommandService productsCommandService,
                OrdersCommandService ordersCommandService,
                OrderDetailsCommandService orderDetailsCommandService) {
        this.userCommandService = userCommandService;
        this.userQuerryService = userQuerryService;
        this.productsQuerryService = productsQuerryService;
        this.ordersQuerryService = ordersQuerryService;
        this.orderDetailsQuerryService = orderDetailsQuerryService;
        this.productsCommandService = productsCommandService;
        this.ordersCommandService = ordersCommandService;
        this.orderDetailsCommandService = orderDetailsCommandService;
        this.scanner = new Scanner(System.in);
        registerMenuActions();
    }

    private void registerMenuActions() {
        registerAction(1, MenuCategory.READ, "Afiseaza toti clientii", this::viewFindAllCustomers);
        registerAction(2, MenuCategory.READ, "Afiseaza toate comenzile", this::viewFindAllOrders);
        registerAction(3, MenuCategory.READ, "Afiseaza toate detaliile comenzilor", this::viewFindAllOrderDetails);
        registerAction(4, MenuCategory.READ, "Afiseaza toate produsele", this::viewFindAllProducts);
        registerAction(5, MenuCategory.CREATE, "Creare produs", this::viewCreateProduct);
        registerAction(6, MenuCategory.CREATE, "Creare detalii comanda", this::viewCreateOrderDetails);
        registerAction(7, MenuCategory.CREATE, "Creare comanda", this::viewCreateOrder);
        registerAction(8, MenuCategory.CREATE, "Creare client", this::viewCreateCustomer);
    }

    private void registerAction(int option, MenuCategory category, String description, Runnable handler) {
        menuActions.put(option, new MenuAction(description, handler, category));
    }

    public void play() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Alege o optiune: ");
            if (choice == 0) {
                running = false;
                continue;
            }

            MenuAction action = menuActions.get(choice);
            if (action == null) {
                System.out.println("Optiune necunoscuta.");
                continue;
            }

            try {
                action.handler().run();
            } catch (RuntimeException e) {
                System.out.println("Eroare: " + e.getMessage());
            }
        }
        System.out.println("Iesire din aplicatie.");
    }

    private void printMenu() {
        System.out.println("\nMeniu Online Shop");
        printMenuSection("CITIRE", MenuCategory.READ);
        printMenuSection("CREARE", MenuCategory.CREATE);
        System.out.println("0 -> Iesire");
    }

    private void printMenuSection(String label, MenuCategory category) {
        System.out.println(label);
        menuActions.forEach((option, action) -> {
            if (action.category() == category) {
                System.out.println(option + " -> " + action.description());
            }
        });
    }

    // --- CREATE ---
    public void viewCreateProduct() {
        System.out.println("\nCreare produs");
        String sku = readLine("SKU: ");
        String name = readLine("Nume: ");
        int price = readInt("Pret: ");
        int weight = readInt("Greutate: ");
        String descriptions = readLine("Descriere: ");
        String category = readLine("Categorie: ");
        int stock = readInt("Stoc: ");

        ProductsDto dto = new ProductsDto(sku, name, price, weight, descriptions, category, new Date(), stock);
        try {
            ProductsResponse saved = productsCommandService.createProduct(dto);
            System.out.println("Produsul " + saved.name() + " a fost creat.");
        } catch (RuntimeException e) {
            System.out.println("Eroare la creare produs: " + e.getMessage());
        }
    }

    public void viewCreateOrderDetails() {
        System.out.println("\nCreare detalii comanda");
        long productId = readLong("ID produs: ");
        int price = readInt("Pret: ");
        String sku = readLine("SKU: ");
        int quantity = readInt("Cantitate: ");

        OrderDetailsDto dto = new OrderDetailsDto(price, sku, quantity, productId);
        try {
            OrderDetailsResponse saved = orderDetailsCommandService.createOrderDetails(dto);
            System.out.println("Detaliile comenzii " + saved.id() + " au fost create.");
        } catch (ProductDoesntExistException e) {
            System.out.println("Produsul nu exista: " + e.getMessage());
        }
    }

    public void viewCreateOrder() {
        System.out.println("\nCreare comanda");
        long customerId = readLong("ID client: ");
        int ammount = readInt("Total: ");
        String shippingAdress = readLine("Adresa livrare: ");
        String orderAddress = readLine("Adresa comanda: ");
        String orderEmail = readLine("Email comanda: ");
        String orderStatus = readLine("Status comanda: ");

        Set<OrderDetailsDto> detailsSet = new HashSet<>();
        while (confirm("Adaugi detalii comanda? (da/nu): ")) {
            long productId = readLong("ID produs: ");
            int price = readInt("Pret: ");
            int quantity = readInt("Cantitate: ");
            String sku = readLine("SKU detaliu: ");
            detailsSet.add(new OrderDetailsDto(price, sku, quantity, productId));
        }

        OrdersDto dto = new OrdersDto(ammount, shippingAdress, orderAddress, orderEmail, LocalDate.now(), orderStatus, detailsSet);
        try {
            OrdersResponse saved = ordersCommandService.createOrder(customerId, dto);
            System.out.println("Comanda " + saved.id() + " a fost creata pentru clientul " + customerId + ".");
        } catch (CustomerDoesntExistException | ProductDoesntExistException e) {
            System.out.println("Eroare: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Eroare la creare comanda: " + e.getMessage());
        }
    }

    public void viewCreateCustomer() {
        System.out.println("\nCreare client");
        String email = readLine("Email: ");
        String password = readLine("Parola: ");
        String fullName = readLine("Nume complet: ");
        String billingAddress = readLine("Adresa facturare: ");
        String defaultShippingAddress = readLine("Adresa default livrare: ");
        String country = readLine("Tara: ");
        String phone = readLine("Telefon: ");

        CustomerDto dto = new CustomerDto(email, password, billingAddress, fullName, defaultShippingAddress, country, phone, new HashSet<>());
        try {
            CustomerResponse saved = userCommandService.createCustomer(dto);
            System.out.println("Clientul " + saved.fullName() + " a fost creat.");
        } catch (RuntimeException e) {
            System.out.println("Eroare la creare client: " + e.getMessage());
        }
    }

    // --- READ ---
    public void viewFindAllCustomers() {
        System.out.println("\nAfiseaza toti clientii");
        PaginationInput pagination = readPagination();
        CustomerListResponse response = userQuerryService.findAllCustomers(pagination.page(), pagination.size());
        List<CustomerResponse> customerList = response.customers();
        if (customerList == null || customerList.isEmpty()) {
            System.out.println("Nu s-a gasit niciun client pentru pagina selectata.");
            return;
        }
        printPageInfo(response.totalElements(), response.totalPages(), response.pageNumber(), response.pageSize());
        customerList.forEach(this::printCustomer);
    }

    private void printCustomer(CustomerResponse customer) {
        System.out.println("ID: " + customer.id() +
                " | Nume: " + customer.fullName() +
                " | Email: " + customer.email() +
                " | Tara: " + customer.country());
        if (customer.orderSet() == null || customer.orderSet().isEmpty()) {
            return;
        }
        System.out.println("  Comenzi:");
        customer.orderSet().forEach(order -> {
            System.out.println("  - ID: " + order.id() +
                    " | Adresa livrare: " + order.shippingAdress() +
                    " | Status: " + order.orderStatus());
            if (order.orderDetailsSet() == null || order.orderDetailsSet().isEmpty()) {
                return;
            }
            System.out.println("      Detalii comanda:");
            order.orderDetailsSet().forEach(detail -> System.out.println("        * SKU: " + detail.sku() +
                    " | Cantitate: " + detail.quantity() +
                    " | Produs: " + detail.product().name() +
                    " | Pret: " + detail.product().price()));
        });
    }

    public void viewFindAllOrders() {
        System.out.println("\nAfiseaza toate comenzile");
        PaginationInput pagination = readPagination();
        OrdersListResponse response = ordersQuerryService.findAllOrders(pagination.page(), pagination.size());
        List<OrdersResponse> ordersList = response.ordersList();
        if (ordersList == null || ordersList.isEmpty()) {
            System.out.println("Nu s-a gasit nicio comanda pentru pagina selectata.");
            return;
        }
        printPageInfo(response.totalElements(), response.totalPages(), response.pageNumber(), response.pageSize());
        ordersList.forEach(this::printOrder);
    }

    private void printOrder(OrdersResponse order) {
        System.out.println("ID: " + order.id() +
                " | Adresa: " + order.orderAddress() +
                " | Status: " + order.orderStatus());
        if (order.orderDetailsSet() == null || order.orderDetailsSet().isEmpty()) {
            return;
        }
        System.out.println("  Detalii comanda:");
        order.orderDetailsSet().forEach(detail -> System.out.println("  - SKU: " + detail.sku() +
                " | Cantitate: " + detail.quantity() +
                " | Produs: " + detail.product().name()));
    }

    public void viewFindAllOrderDetails() {
        System.out.println("\nAfiseaza toate detaliile comenzilor");
        PaginationInput pagination = readPagination();
        OrderDetailsListResponse response = orderDetailsQuerryService.findAllOrderDetails(pagination.page(), pagination.size());
        List<OrderDetailsResponse> detailsList = response.orderDetailsList();
        if (detailsList == null || detailsList.isEmpty()) {
            System.out.println("Nu s-a gasit niciun detaliu pentru pagina selectata.");
            return;
        }
        printPageInfo(response.totalElements(), response.totalPages(), response.pageNumber(), response.pageSize());
        detailsList.forEach(this::printOrderDetail);
    }

    private void printOrderDetail(OrderDetailsResponse detail) {
        System.out.println("ID: " + detail.id() +
                " | SKU: " + detail.sku() +
                " | Cantitate: " + detail.quantity() +
                " | Pret: " + detail.price() +
                " | Produs: " + detail.product().name());
    }

    public void viewFindAllProducts() {
        System.out.println("\nAfiseaza toate produsele");
        PaginationInput pagination = readPagination();
        ProductsListResponse response = productsQuerryService.findAllProducts(pagination.page(), pagination.size());
        List<ProductsResponse> products = response.productsList();
        if (products == null || products.isEmpty()) {
            System.out.println("Nu s-a gasit niciun produs pentru pagina selectata.");
            return;
        }
        printPageInfo(response.totalElements(), response.totalPages(), response.pageNumber(), response.pageSize());
        products.forEach(this::printProduct);
    }

    private void printProduct(ProductsResponse product) {
        System.out.println("ID: " + product.id() +
                " | Nume: " + product.name() +
                " | SKU: " + product.sku() +
                " | Pret: " + product.price() +
                " | Stoc: " + product.stock());
    }

    private void printPageInfo(long totalElements, int totalPages, int pageNumber, int pageSize) {
        System.out.println("Pagina " + (pageNumber + 1) + "/" + Math.max(totalPages, 1) +
                " | Elemente/pagina: " + pageSize +
                " | Total inregistrari: " + totalElements);
    }

    private PaginationInput readPagination() {
        int page = readIntWithDefault("Pagina (>=0)", 0, 0);
        int size = readIntWithDefault("Dimensiune pagina (>0)", 10, 1);
        return new PaginationInput(page, size);
    }

    // --- INPUT HELPERS ---
    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                System.out.println("Introdu un numar intreg valid.");
            }
        }
    }

    private int readIntWithDefault(String prompt, int defaultValue, int minValue) {
        while (true) {
            System.out.print(prompt + " [default " + defaultValue + "]: ");
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                return defaultValue;
            }
            try {
                int parsed = Integer.parseInt(value);
                if (parsed < minValue) {
                    System.out.println("Valoarea trebuie sa fie >= " + minValue + ".");
                    continue;
                }
                return parsed;
            } catch (NumberFormatException e) {
                System.out.println("Introdu un numar valid.");
            }
        }
    }

    private long readLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            try {
                return Long.parseLong(value.trim());
            } catch (NumberFormatException e) {
                System.out.println("Introdu un numar valid.");
            }
        }
    }

    private boolean confirm(String prompt) {
        return readLine(prompt).equalsIgnoreCase("da");
    }
}
