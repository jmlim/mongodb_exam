package io.jmlim.mongoex.demo.runner;

import io.jmlim.mongoex.demo.domain.category.Ancestor;
import io.jmlim.mongoex.demo.domain.category.Category;
import io.jmlim.mongoex.demo.domain.product.Detail;
import io.jmlim.mongoex.demo.domain.common.Price;
import io.jmlim.mongoex.demo.domain.product.PriceHistory;
import io.jmlim.mongoex.demo.domain.product.Product;
import io.jmlim.mongoex.demo.repository.CategoryRepository;
import io.jmlim.mongoex.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * TODO: 테스트 중..
 */
@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) {

        categoryRepository.deleteAll();
        productRepository.deleteAll();

        List<Ancestor> ancestors = new ArrayList<>();

        Category home = Category.builder().slug("home")
                .name("Home")
                .description("Jmlim Home").build();

        home = categoryRepository.insert(home);

        ancestors.add(Ancestor.builder()
                .slug(home.getSlug())
                .name(home.getName())
                .id(home.getId())
                .build());

        Category outdoors = Category.builder().slug("outdoors")
                .name("Outdoors")
                .description("outdoor~~ jmlim")
                .parentId(home.getId())
                .ancestors(ancestors).build();

        outdoors = categoryRepository.insert(outdoors);

        ancestors.add(Ancestor.builder()
                .slug(outdoors.getSlug())
                .name(outdoors.getName())
                .id(outdoors.getId())
                .build());

        Category gardeningTool = Category.builder().slug("gardening-tools")
                .name("Gardening Tools")
                .description("Gardening gadgets galore!")
                .parentId(outdoors.getId())
                .ancestors(ancestors).build();

        gardeningTool = categoryRepository.insert(gardeningTool);

        List<PriceHistory> priceHistories = new ArrayList<>();
        List<ObjectId> categoryIds = new ArrayList<>();

        ObjectId primaryCategory;
        Detail detail = Detail.builder()
                .weight(47)
                .weightUnits("lbs")
                .modelNum(4039283402l)
                .manufacturer("Acme")
                .color("Green")
                .build();
        Price price = Price.builder()
                .retail(589700)
                .sale(489700)
                .build();

        PriceHistory priceHistory1 = PriceHistory.builder()
                .retail(529700)
                .sale(429700)
                .start(new Date())
                .end(new Date()).build();

        priceHistories.add(priceHistory1);

        PriceHistory priceHistory2 = PriceHistory.builder()
                .retail(529700)
                .sale(529700)
                .start(new Date())
                .end(new Date()).build();

        priceHistories.add(priceHistory2);


        ObjectId gardeningToolId = gardeningTool.getId();
        categoryIds.add(gardeningToolId);

        Product product = Product.builder().slug("wheelbarrow-9092")
                .sku("9092")
                .name("Extra Large Wheelbarrow")
                .description("Heavy duty wheelbarrow...")
                .details(detail)
                .totalReviews(4)
                .averageReview(4.5)
                .pricing(price)
                .priceHistory(priceHistories)
                .primaryCategory(gardeningToolId)
                .categoryIds(categoryIds)
                .mainCatId(gardeningToolId)
                .tags(Arrays.asList("tools", "gardening", "soil")).build();

        productRepository.insert(product);
    }
}

