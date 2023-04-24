package wiwiel.training.bootstrap;

import wiwiel.training.domain.Category;
import wiwiel.training.domain.Customer;
import wiwiel.training.domain.Vendor;
import wiwiel.training.repositories.CategoryRepository;
import wiwiel.training.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import wiwiel.training.repositories.VendorRepository;

@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository
            , CustomerRepository customerRepository
            , VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Categories Loaded: " + categoryRepository.count());
    }

    private void loadCustomers() {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1l);
        customer1.setFirstName("Michale");
        customer1.setLastName("Weston");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setId(2l);
        customer2.setFirstName("Sam");
        customer2.setLastName("Axe");

        customerRepository.save(customer2);

        System.out.println("Customers Loaded: " + customerRepository.count());
    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("Western Tasty Fruits Ltd.");

        Vendor vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("Exotic Fruits Company");

        Vendor vendor3 = new Vendor();
        vendor3.setId(3L);
        vendor3.setName("Home Fruits");

        Vendor vendor4 = new Vendor();
        vendor4.setId(4L);
        vendor4.setName("Fun Fresh Fruits Ltd.");

        Vendor vendor5 = new Vendor();
        vendor5.setId(5L);
        vendor5.setName("Nuts for Nuts Company");

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);
        vendorRepository.save(vendor3);
        vendorRepository.save(vendor4);
        vendorRepository.save(vendor5);

        System.out.println("Vendors Loaded: " + vendorRepository.count());
    }
}
