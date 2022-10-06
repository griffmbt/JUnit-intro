package quru.qa;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class WebTest {

    @CsvSource(value = {
            "DOM, Теневой DOM",
            "JS, Мне часто задают вопрос"
    })
    @ParameterizedTest(name = "Результат поиска содержит {1} для запроса {0}")
    void learnJavascriptSearchTest(String testData, String expectedResult) {
        open("https://learn.javascript.ru/");
        $(".text-input__frontpage-search__input").setValue(testData);
        $(".frontpage-search__button").click();
        $(".search-results .search-results__extract")
                .shouldHave(text(expectedResult));
    }
    static Stream<Arguments> checkTabsMenu() {
        return Stream.of(
                Arguments.of("English", List.of(
                        "The JavaScript language",
                        "Browser: Document, Events, Interfaces",
                        "Additional articles"
                )),
                Arguments.of("Русский", List.of(
                        "Язык JavaScript",
                        "Браузер: документ, события, интерфейсы",
                        "Тематические разделы"
                ))
        );
    }

    @MethodSource("checkTabsMenu")
    @ParameterizedTest(name = "Для языка {0} отображаются меню {1}")
    void checkLocalization(String lang, List<String> expectedLink) {
        open("https://learn.javascript.ru/");
        $(".sitetoolbar__lang-switcher .sitetoolbar__dropdown-button").click();
        $$(".supported-langs__container a").find(text(lang)).click();
        $$(".tabs__menu-inner-scroll a").shouldHave(CollectionCondition.texts(expectedLink));

    }
}


