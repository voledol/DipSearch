import model.Page;
import model.PageRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {
    private final PageRepository pageRepository;

    // Рекомендуемый вариант внедрения зависимости:
    // внедрение зависимости в класс через конструктор
    public PageController(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }
    @PostMapping("/books/")
    public int add(Page page) {
        Page newBook = pageRepository.save(page);
        return newBook.getId();
    }
}
