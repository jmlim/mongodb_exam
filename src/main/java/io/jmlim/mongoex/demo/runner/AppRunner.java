package io.jmlim.mongoex.demo.runner;

import io.jmlim.mongoex.demo.domain.category.Ancestor;
import io.jmlim.mongoex.demo.domain.category.Category;
import io.jmlim.mongoex.demo.domain.order.LineItem;
import io.jmlim.mongoex.demo.domain.order.Order;
import io.jmlim.mongoex.demo.domain.order.ShippingAddress;
import io.jmlim.mongoex.demo.domain.product.Detail;
import io.jmlim.mongoex.demo.domain.common.Price;
import io.jmlim.mongoex.demo.domain.product.PriceHistory;
import io.jmlim.mongoex.demo.domain.product.Product;
import io.jmlim.mongoex.demo.domain.review.Review;
import io.jmlim.mongoex.demo.domain.user.Address;
import io.jmlim.mongoex.demo.domain.user.PaymentMethod;
import io.jmlim.mongoex.demo.domain.user.User;
import io.jmlim.mongoex.demo.repository.*;
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

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final ReviewRepository reviewRepository;

    @Override
    public void run(ApplicationArguments args) {

        categoryRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
        orderRepository.deleteAll();
        reviewRepository.deleteAll();

        addSampleCategoryAndProduct();

        addSampleUser();

        addSampleOrder();

        addSampleReview();
    }

    private void addSampleCategoryAndProduct() {

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

    private void addSampleUser() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(Address.builder()
                .name("home")
                .street("588 5th Street")
                .city("Brooklyn")
                .street("NY")
                .zip(11215).build());
        addresses.add(Address.builder()
                .name("work")
                .street("1 E. 23rd Street")
                .city("New York")
                .street("NY")
                .zip(10010).build());

        List<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(PaymentMethod.builder()
                .name("VISA")
                .paymentToken("43f6ba1dfda65b8106dc7").build());

        User newUser = User.builder()
                .username("kbanker")
                .email("kylebanker@gmail.com")
                .firstName("Kyle")
                .lastName("Banker")
                .hashPassword("bd1cfa194c4a603e7186780824b04419")
                .address(addresses)
                .paymentMethod(paymentMethods)
                .build();

        userRepository.insert(newUser);
    }


    private void addSampleOrder() {
        // order
        // find user
        Optional<User> optionalUser = userRepository.findByUsername("kbanker");

        optionalUser.ifPresent(user -> {

            Optional<Product> optionalProduct = productRepository.findBySlug("wheelbarrow-9092");

            List<LineItem> lineItems = new ArrayList<>();
            optionalProduct.ifPresent(bySlug -> {
                Price pricing = bySlug.getPricing();

                LineItem newLineItem = LineItem.builder()
                        .id(bySlug.getId())
                        .name(bySlug.getName())
                        .quantity(1)
                        .sku(bySlug.getSku())
                        .pricing(pricing)
                        .build();

                lineItems.add(newLineItem);
            });

            Address address = user.getAddress().get(0);
            ShippingAddress shippingAddress = ShippingAddress.builder().street(address.getStreet())
                    .city(address.getCity())
                    .state(address.getState())
                    .zip(address.getZip())
                    .build();

            Order newOrder = Order.builder()
                    .userId(user.getId())
                    .state("CART")
                    .lineItems(lineItems)
                    .shippingAddress(shippingAddress)
                    .subTotal(4897)
                    .build();

            orderRepository.insert(newOrder);
        });
    }

    private void addSampleReview() {
        // reviews
        // find user
        Optional<User> optionalUser = userRepository.findByUsername("kbanker");

        optionalUser.ifPresent(user -> {
            Optional<Product> optionalProduct = productRepository.findBySlug("wheelbarrow-9092");
            optionalProduct.ifPresent(product -> {
                Review newReview = Review.builder()
                        .productId(product.getId())
                        .date(new Date())
                        .title("Amazing")
                        .text("Has a squeaky wheel, but still a darn good wheelbarrow.")
                        .rating(4)
                        .userId(user.getId())
                        .username(user.getUsername())
                        .helpfulVotes(0)
                        .voterIds(new ArrayList<>())
                        .build();

                reviewRepository.insert(newReview);
            });
        });
    }
}

