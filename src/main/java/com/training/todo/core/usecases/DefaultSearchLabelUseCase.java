package com.training.todo.core.usecases;

import com.training.todo.core.domain.ReadLabel;
import com.training.todo.core.usecases.repository.ILabelRepository;

public class DefaultSearchLabelUseCase implements ISearchLabelUseCase {

    private final ILabelRepository labelRepository;

    public DefaultSearchLabelUseCase(ILabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public Response execute(Request request) throws ValidationException {
        ILabelRepository.DBLabel dbLabel;
        try {
            dbLabel = labelRepository.search(request.id())
                    .orElseThrow(() -> new ValidationException("Label not found"));
        } catch (ILabelRepository.DBException e) {
            throw new ValidationException("No such Label", e);
        }
        return new Response(ReadLabel.builder()
                .id(dbLabel.id())
                .title(dbLabel.title())
                .description(dbLabel.description())
                .build());
    }
}
