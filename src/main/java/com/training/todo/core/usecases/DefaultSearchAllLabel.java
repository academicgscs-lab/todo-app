package com.training.todo.core.usecases;

import com.training.todo.core.domain.ReadLabel;
import com.training.todo.core.usecases.repository.ILabelRepository;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class DefaultSearchAllLabel implements ISearchAllLabelUseCase {
    private final ILabelRepository labelRepository;

    private ReadLabel mapToReadLabel(ILabelRepository.DBLabel label) {
        return ReadLabel.builder()
                .id(label.id())
                .title(label.title())
                .description(label.description())
                .build();
    }


    @Override
    public Response execute() throws ValidationException, ILabelRepository.DBException {
        new Response(labelRepository.searchAll().stream().map(this::mapToReadLabel).collect(Collectors.toList()));
    }
}
