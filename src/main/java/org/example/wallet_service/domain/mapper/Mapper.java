package org.example.wallet_service.domain.mapper;

/**
 * Интерфейс, предоставляющий возможность смапить dto объект к соответствующей сущности
 * @param <F> - класс объекта, который нужно смапить
 * @param <T> - класс спамленного объекта
 */
    public interface Mapper<F, T> {
        T map(F object);
    }

