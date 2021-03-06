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
import io.jmlim.mongoex.demo.service.AggregationSampleService;
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

    private final AggregationSampleService aggregationSampleService;

    @Override
    public void run(ApplicationArguments args) {

        categoryRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
        orderRepository.deleteAll();
        reviewRepository.deleteAll();

        for (int i = 0; i < 500; i++) {
            addSampleCategoryAndProduct(i);
            addSampleUser(i);
            addSampleOrder(i);
            addSampleReview(i);
        }
    }

    private void addSampleCategoryAndProduct(int index) {

        List<Ancestor> ancestors = new ArrayList<>();

        Category home = Category.builder().slug("home" + index)
                .name("Home" + index)
                .description("Jmlim Home" + index).build();

        home = categoryRepository.insert(home);

        ancestors.add(Ancestor.builder()
                .slug(home.getSlug())
                .name(home.getName())
                .id(home.getId())
                .build());

        Category outdoors = Category.builder().slug("outdoors" + index)
                .name("Outdoors" + index)
                .description("outdoor~~ jmlim" + index)
                .parentId(home.getId())
                .ancestors(ancestors).build();

        outdoors = categoryRepository.insert(outdoors);

        ancestors.add(Ancestor.builder()
                .slug(outdoors.getSlug())
                .name(outdoors.getName())
                .id(outdoors.getId())
                .build());

        Category gardeningTool = Category.builder().slug("gardening-tools" + index)
                .name("Gardening Tools" + index)
                .description("Gardening gadgets galore!" + index)
                .parentId(outdoors.getId())
                .ancestors(ancestors).build();

        gardeningTool = categoryRepository.insert(gardeningTool);

        for (int i = 0; i < 20; i++) {
            List<PriceHistory> priceHistories = new ArrayList<>();
            List<ObjectId> categoryIds = new ArrayList<>();

            Detail detail = Detail.builder()
                    .weight(47)
                    .weightUnits("lbs" + i)
                    .modelNum(4039283402l)
                    .manufacturer("Acme" + i)
                    .color("Green" + i)
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

            Product product = Product.builder().slug("wheelbarrow" + index + "-9092" + i)
                    .sku(index + "9092" + i)
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
                    .tags(Arrays.asList("tools", "gardening", "soil" + i)).build();

            productRepository.insert(product);
        }
    }

    private void addSampleUser(int index) {
        List<Address> addresses = new ArrayList<>();
        addresses.add(Address.builder()
                .name("home" + index)
                .street("588 5th Street" + index)
                .city("Brooklyn" + index)
                .street("NY" + index)
                .zip(11215).build());
        addresses.add(Address.builder()
                .name("work" + index)
                .street("1 E. 23rd Street" + index)
                .city("New York" + index)
                .street("NY" + index)
                .zip(10010).build());

        List<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(PaymentMethod.builder()
                .name("VISA")
                .paymentToken("43f6ba1dfda65b8106dc7" + index).build());

        User newUser = User.builder()
                .username("kbanker" + index)
                .email("kylebanker" + index + "@gmail.com")
                .firstName("Kyle" + index)
                .lastName("Banker" + index)
                .hashPassword("bd1cfa194c4a603e7186780824b04419" + index)
                .address(addresses)
                .paymentMethod(paymentMethods)
                .build();

        userRepository.insert(newUser);
    }


    private void addSampleOrder(int index) {
        // order
        // find user
        Optional<User> optionalUser = userRepository.findByUsername("kbanker" + index);

        optionalUser.ifPresent(user -> {
            for (int i = 0; i < 20; i++) {
                Optional<Product> optionalProduct = productRepository.findBySlug("wheelbarrow" + index + "-9092" + i);

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
            }
        });
    }

    private void addSampleReview(int index) {
        // reviews
        // find user
        Optional<User> optionalUser = userRepository.findByUsername("kbanker");

        optionalUser.ifPresent(user -> {
            for (int i = 0; i < 20; i++) {
                Optional<Product> optionalProduct = productRepository.findBySlug("wheelbarrow" + index + "-9092" + i);
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
            }
        });
    }
}

