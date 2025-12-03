package mycode.onlineshopspring.view;

import mycode.onlineshopspring.customer.repository.CustomerRepository;
import mycode.onlineshopspring.customer.service.UserCommandService;
import mycode.onlineshopspring.customer.service.UserQuerryService;
import mycode.onlineshopspring.products.service.ProductsQuerryService;
import mycode.onlineshopspring.orders.service.OrdersQuerryService;
import mycode.onlineshopspring.orderDetails.service.OrderDetailsQuerryService;
import mycode.onlineshopspring.products.service.ProductsCommandService;
import mycode.onlineshopspring.orders.service.OrdersCommandService;
import mycode.onlineshopspring.orderDetails.service.OrderDetailsCommandService;
import mycode.onlineshopspring.customer.dto.CustomerResponse;
import mycode.onlineshopspring.products.dto.ProductsListResponse;
import mycode.onlineshopspring.orders.dto.OrdersListResponse;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsListResponse;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsResponse;
import mycode.onlineshopspring.orders.dto.OrdersResponse;
import mycode.onlineshopspring.customer.dto.CustomerDto;
import mycode.onlineshopspring.orders.dto.OrdersDto;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsDto;
import mycode.onlineshopspring.products.dto.ProductsDto;
import mycode.onlineshopspring.products.dto.ProductsResponse;
import mycode.onlineshopspring.exceptions.ProductDoesntExistException;
import mycode.onlineshopspring.exceptions.CustomerDoesntExistException;
import mycode.onlineshopspring.utils.Cart;
import mycode.onlineshopspring.utils.dto.CartItemDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class View{
    private UserQuerryService userQuerryService;
    private UserCommandService userCommandService;
    private ProductsQuerryService productsQuerryService;
    private OrdersQuerryService ordersQuerryService;
    private OrderDetailsQuerryService orderDetailsQuerryService;
    private ProductsCommandService productsCommandService;
    private OrdersCommandService ordersCommandService;
    private OrderDetailsCommandService orderDetailsCommandService;
    private CustomerRepository customerRepository;
    private final Scanner scanner;
    private Cart cart;

    public View(UserCommandService userCommandService,UserQuerryService userQuerryService,CustomerRepository customerRepository,ProductsQuerryService productsQuerryService,OrdersQuerryService ordersQuerryService,OrderDetailsQuerryService orderDetailsQuerryService,ProductsCommandService productsCommandService,OrdersCommandService ordersCommandService,OrderDetailsCommandService orderDetailsCommandService,Cart cart){

        this.userCommandService=userCommandService;
        this.userQuerryService=userQuerryService;
        this.customerRepository=customerRepository;
        this.productsQuerryService=productsQuerryService;
        this.ordersQuerryService=ordersQuerryService;
        this.orderDetailsQuerryService=orderDetailsQuerryService;
        this.productsCommandService=productsCommandService;
        this.ordersCommandService=ordersCommandService;
        this.orderDetailsCommandService=orderDetailsCommandService;
        this.cart=cart;
        this.scanner=new Scanner(System.in);
    }

    public void menu(){
        System.out.println("MeniuOnlineShop");
        System.out.println("CITIRE");
        System.out.println("1->Afiseaza ttoti Clientii");
        System.out.println("2->Afiseaza  toate comenzile");
        System.out.println("3->Afiseaza toate detaliile comenzilor");
        System.out.println("4->afiseaza toate produsele");
        System.out.println("CREARE");
        System.out.println("5->CreareProdus");
        System.out.println("6->CreareDetaliiComanda");
        System.out.println("7->CreareComanda(laClient)");
        System.out.println("8->CreareClient");
        System.out.println("0->Iesire");
        System.out.print("Alegeooptiune:");
    }

    public void play(){
        boolean merge=true;
        while(merge){
            this.menu();
            int choice=scanner.nextInt();
            scanner.nextLine();
            try{
                switch(choice){
                    case 1:
                        viewFindAllCustomers();
                        break;
                    case 2:
                        viewFindAllOrders();
                        break;
                    case 3:
                        viewFindAllOrderDetails();
                        break;
                    case 4:
                        viewFindAllProducts();
                        break;
                    case 5:
                        viewCreateProduct();
                        break;
                    case 6:
                        viewCreateOrderDetails();
                        break;
                    case 7:
                        viewCreateOrder();
                        break;
                    case 8:
                        viewCreateCustomer();
                        break;
                    case 0:
                        merge=false;
                        System.out.println("Iesire din aplicatie.");
                        break;
                    default:
                        System.out.println("Optiune necunoscuta.");
                        break;
                }
            }catch(RuntimeException e){
                System.out.println("Eroare:"+e.getMessage());
            }
        }
    }

    // --- CREATE ---
    public void viewCreateProduct(){
        System.out.println("Creare Produs");
        System.out.print("SKU:");
        String sku=scanner.nextLine();
        System.out.print("Nume:");
        String name=scanner.nextLine();
        System.out.print("Pret:");
        int price=scanner.nextInt();
        System.out.print("Greutate:");
        int weight=scanner.nextInt();
        scanner.nextLine();
        System.out.print("Descriere:");
        String descriptions=scanner.nextLine();
        System.out.print("Categorie:");
        String category=scanner.nextLine();
        System.out.print("Stoc:");
        int stock=scanner.nextInt();
        scanner.nextLine();

        ProductsDto dto=new ProductsDto(sku,name,price,weight,descriptions,category,new Date(),stock);
        try{
            ProductsResponse saved=productsCommandService.createProduct(dto);
            System.out.println("Produsul "+saved.name()+" a fost creat.");
        }catch(RuntimeException e){
            System.out.println("Eroare la creare Produs:"+e.getMessage());
        }
    }

    public void viewCreateOrderDetails(){
        System.out.println("Crear detalii comanda");
        System.out.print("ID Produs:");
        Long productId=scanner.nextLong();
        System.out.print("Pret:");
        int price=scanner.nextInt();
        System.out.print("SKU:");
        scanner.nextLine();
        String sku=scanner.nextLine();
        System.out.print("Cantitate:");
        int quantity=scanner.nextInt();
        scanner.nextLine();

        OrderDetailsDto dto=new OrderDetailsDto(price,sku,quantity,productId);
        try{
            OrderDetailsResponse saved=orderDetailsCommandService.createOrderDetails(dto);
            System.out.println("Detaliile comenzii "+saved.id()+" a fost creat.");
        }catch(ProductDoesntExistException e){
            e.printStackTrace();
        }
    }

    public void viewCreateOrder(){
        System.out.println("Creare Comanda");
        System.out.print("ID Client:");
        Long customerId=scanner.nextLong();
        System.out.print("Total:");
        int ammount=scanner.nextInt();
        scanner.nextLine();
        System.out.print("Adresa  Livrare:");
        String shippingAdress=scanner.nextLine();
        System.out.print("Adresa Comanda:");
        String orderAddress=scanner.nextLine();
        System.out.print("Email Comanda:");
        String orderEmail=scanner.nextLine();
        System.out.print("Status Comanda:");
        String orderStatus=scanner.nextLine();


        Set<OrderDetailsDto>detailsSet=new HashSet<>();
        System.out.print("Adaugi detalii comanda?(da/nu):");
        if(scanner.nextLine().equalsIgnoreCase("da")){
            System.out.print("IDvProdus:");
            Long productId=scanner.nextLong();
            System.out.print("Pret:");
            int price=scanner.nextInt();
            System.out.print("Cantitate:");
            int quantity=scanner.nextInt();
            System.out.print("SKUdetalii:");
            scanner.nextLine();
            String sku=scanner.nextLine();

            detailsSet.add(new OrderDetailsDto(price,sku,quantity,productId));
        }

        OrdersDto dto=new OrdersDto(ammount,shippingAdress,orderAddress,orderEmail,LocalDate.now(),orderStatus,detailsSet);
        try{
            OrdersResponse saved=ordersCommandService.createOrder(customerId,dto);
            System.out.println("Comanda "+saved.id()+" a fost creata pentru clientul "+customerId+".");
        }catch(CustomerDoesntExistException | ProductDoesntExistException e){
            System.out.println("Eroare:"+e.getMessage());
        }catch(RuntimeException e){
            System.out.println("EroarelaCreareComanda:"+e.getMessage());
        }
    }

    public void viewCreateCustomer(){
        System.out.println("CreareClient");
        System.out.print("Email:");
        String email=scanner.nextLine();
        System.out.print("Parola:");
        String password=scanner.nextLine();
        System.out.print("NumeComplet:");
        String fullName=scanner.nextLine();
        System.out.print("AdresaFacturare:");
        String billingAddress=scanner.nextLine();
        System.out.print("AdresaDefaultLivrare:");
        String defaultShippingAddress=scanner.nextLine();
        System.out.print("Tara:");
        String country=scanner.nextLine();
        System.out.print("Telefon:");
        String phone=scanner.nextLine();

        Set<OrdersDto>ordersSet=new HashSet<>();

        CustomerDto dto=new CustomerDto(email,password,billingAddress,fullName,defaultShippingAddress,country,phone,ordersSet);
        try{
            CustomerResponse saved=userCommandService.createCustomer(dto);
            System.out.println("Clientul "+saved.fullName()+" a fost creat.");
        }catch(RuntimeException e){
            System.out.println("EroarelaCreareClient:"+e.getMessage());
        }
    }


    public void viewFindAllCustomers(){
        System.out.println("Afiseaza toti clientii");
        List<CustomerResponse>customerList=userQuerryService.findAllCustomers();

        if(customerList==null||customerList.isEmpty()){
            System.out.println("Nu sa gasit niciun client.");
            return;
        }
        customerList.forEach(c->{
            System.out.println("ID:"+c.id()+"|Nume:"+c.fullName()+"|Email:"+c.email()+"|Tara:"+c.country());
            if(!c.orderSet().isEmpty()){
                System.out.println("Comenzi:");
                c.orderSet().forEach(o->{
                    System.out.println("ID:"+o.id()+"|Adresa Livrare:"+o.shippingAdress()+"|Status:"+o.orderStatus());
                    if(!o.orderDetailsSet().isEmpty()){
                        System.out.println("DetaliiComanda:");
                        o.orderDetailsSet().forEach(d->{
                            System.out.println("SKU:"+d.sku()+"|Cantitate:"+d.quantity()+"|Produs:"+d.product().name()+"|Pret:"+d.product().price());
                        });
                    }
                });
            }
        });
    }
    public void viewFindAllOrders(){
        System.out.println("Afiseaza Toate Comenzile");
        OrdersListResponse response=ordersQuerryService.findAllOrders();
        List<OrdersResponse>ordersList=response.ordersList();

        if(ordersList.isEmpty()){
            System.out.println("Nu s-a gasit nicio comanda.");
            return;
        }
        ordersList.forEach(o->{
            System.out.println("ID:"+o.id()+"|Adresa:"+o.orderAddress()+"|Status:"+o.orderStatus());
            if(!o.orderDetailsSet().isEmpty()){
                System.out.println("DetaliiComanda:");
                o.orderDetailsSet().forEach(d->{
                    System.out.println("SKU:"+d.sku()+"|Cantitate:"+d.quantity()+"|Produs:"+d.product().name());
                });
            }
        });
    }
    public void viewFindAllOrderDetails(){
        System.out.println("Afiseaza Toate Detaliile Comenzilor");
        OrderDetailsListResponse response=orderDetailsQuerryService.findAllOrderDetails();
        List<OrderDetailsResponse>detailsList=response.orderDetailsList();

        if(detailsList.isEmpty()){
            System.out.println("Nu s-agasit niciun detaliu.");
            return;
        }
        detailsList.forEach(d->{
            System.out.println("ID:"+d.id()+"|SKU:"+d.sku()+"|Cantitate:"+d.quantity()+"|Pret:"+d.price()+"|Produs:"+d.product().name());
        });
    }
    public void viewFindAllProducts(){
        System.out.println("Afiseaza Toat Produsele");
        ProductsListResponse response=productsQuerryService.findAllProducts();
        if(response.productsList().isEmpty()){
            System.out.println("Nus-agasitniciunprodus.");
            return;
        }
        response.productsList().forEach(p->{
            System.out.println("ID:"+p.id()+"|Nume:"+p.name()+"|SKU:"+p.sku()+"|Pret:"+p.price()+"|Stoc:"+p.stock());
        });
    }
//    // LOGICA DE CREARE CONSOLIDATĂ
//    public void viewCreateCustomer(){
//        System.out.println("CreareClientSiComandaCompleta");
//        System.out.print("Email:");
//        String email=scanner.nextLine();
//        System.out.print("Parola:");
//        String password=scanner.nextLine();
//        System.out.print("NumeComplet:");
//        String fullName=scanner.nextLine();
//        System.out.print("AdresaFacturare:");
//        String billingAddress=scanner.nextLine();
//        System.out.print("AdresaDefaultLivrare:");
//        String defaultShippingAddress=scanner.nextLine();
//        System.out.print("Tara:");
//        String country=scanner.nextLine();
//        System.out.print("Telefon:");
//        String phone=scanner.nextLine();
//
//        // --- Citire Comanda (OrdersDto) ---
//        System.out.println("---AdaugareComanda---");
//        System.out.print("TotalComanda:");
//        int ammount=scanner.nextInt();
//        scanner.nextLine();
//        System.out.print("AdresaLivrare:");
//        String shippingAdress=scanner.nextLine();
//        System.out.print("StatusComanda:");
//        String orderStatus=scanner.nextLine();
//
//        // --- Citire Detalii Comanda (OrderDetailsDto) ---
//        Set<OrderDetailsDto>detailsSet=new HashSet<>();
//        System.out.println("---AdaugareDetaliiComanda (Necesita Produs Existent)---");
//        System.out.print("IDProdus:");
//        Long productId=scanner.nextLong();
//        System.out.print("PretDetalii:");
//        int price=scanner.nextInt();
//        System.out.print("Cantitate:");
//        int quantity=scanner.nextInt();
//        scanner.nextLine();
//        System.out.print("SKUDetalii:");
//        String sku=scanner.nextLine();
//        detailsSet.add(new OrderDetailsDto(price,sku,quantity,productId));
//
//        // Finalizarea Orders DTO
//        Set<OrdersDto>ordersSet=new HashSet<>();
//        OrdersDto orderDto=new OrdersDto(ammount,shippingAdress,"AdresaComanda",email,LocalDate.now(),orderStatus,detailsSet);
//        ordersSet.add(orderDto);
//
//        // Finalizarea Customer DTO
//        CustomerDto dto=new CustomerDto(email,password,billingAddress,fullName,defaultShippingAddress,country,phone,ordersSet);
//        try{
//            CustomerResponse saved=userCommandService.createCustomer(dto);
//            System.out.println("Clientul "+saved.fullName()+" a fost creat cu comanda completa.");
//        }catch(RuntimeException e){
//            System.out.println("EroarelaCreareClient:"+e.getMessage());
//        }
//    }

    public void viewCheckOut(){
        Set<CartItemDto>items=cart.getCart();
        if(items.isEmpty()){
            System.out.println("COSUL ESTE GOL");
            return;
        }
        System.out.println("ID Client existent: ");
        Long customerId=scanner.nextLong();
        scanner.nextLine();
        System.out.println("Adresa livrare: ");
        String shippingAddress= scanner.nextLine();
        System.out.println("Status comanda: ");
        String orderStatus= scanner.nextLine();

        Set<OrderDetailsDto>orderDetailsDtos=new HashSet<>();
        int totalAmmount=0;
    }
}