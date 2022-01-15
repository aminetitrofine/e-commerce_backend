package org.sid.lightecom;

import net.bytebuddy.utility.RandomString;
import org.sid.lightecom.entities.Category;
import org.sid.lightecom.dao.CategoryRepository;
import org.sid.lightecom.entities.Product;
import org.sid.lightecom.dao.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Random;

@SpringBootApplication
public class LightEcomApplication implements CommandLineRunner {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;
    public static void main(String[] args) {

        SpringApplication.run(LightEcomApplication.class, args);
    }

    @Override
    public void run(String... args)  {
        repositoryRestConfiguration.exposeIdsFor(Product.class, Category.class);

        categoryRepository.save(new Category(null,"Ordinateurs","computer",null,null));
        categoryRepository.save(new Category(null,"Imprimantes","printer",null,null));
        categoryRepository.save(new Category(null,"Smart Phones","smartphone",null,null));
        Random rnd = new Random();
        categoryRepository.findAll().forEach(c->{
            for(int i=0;i<20;i++) {
                Product p = new Product();
                p.setName(RandomString.make(20));
                p.setCurrentPrice(100 + rnd.nextInt(10000));
                p.setAvailable(rnd.nextBoolean());
                p.setPromotion(rnd.nextBoolean());
                p.setCategory(c);
                p.setSelected(rnd.nextBoolean());
                p.setPhotoName("unknownProduct.png");
                productRepository.save(p);
            }
        });

    }
}
