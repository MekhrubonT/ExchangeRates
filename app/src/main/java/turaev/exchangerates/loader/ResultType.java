package turaev.exchangerates.loader;

/**
 * Created by Lenovo on 09.04.2017.
 */

public enum ResultType {
    OK("OK"),
    NO_INTERNET("Отсутствует интернет-соединение"),
    ERROR("Ошибка при получении данных");

    private String message;

    ResultType(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
