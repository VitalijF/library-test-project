package com.vitaliif.library.db.loader;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoaderProcessor {

    private final Map<EntityLoaderType, DataLoader> loaders;

    public LoaderProcessor(@Qualifier("userDataLoader") DataLoader userLoader,
                           @Qualifier("bookCsvDataLoader") DataLoader bookDataLoader,
                           @Qualifier("borrowedCsvDataLoader") DataLoader borrowedDataLoader) {
        loaders = Map.of(
                EntityLoaderType.USER, userLoader,
                EntityLoaderType.BOOK, bookDataLoader,
                EntityLoaderType.BORROWED, borrowedDataLoader
        );
    }

    @PostConstruct
    public void loadData() {
        loaders.get(EntityLoaderType.USER).loadDataIntoTable("users.csv");
        loaders.get(EntityLoaderType.BOOK).loadDataIntoTable("books.csv");
        loaders.get(EntityLoaderType.BORROWED).loadDataIntoTable("borrowed.csv");
    }
}
