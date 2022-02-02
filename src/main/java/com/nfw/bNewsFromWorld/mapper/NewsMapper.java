package com.nfw.bNewsFromWorld.mapper;

import com.nfw.bNewsFromWorld.domain.Publication;
import com.nfw.bNewsFromWorld.domain.PublicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewsMapper {

    public Publication mapToPublication(PublicationDto publicationDto) {
        return new Publication(
                publicationDto.getTitle(),
                publicationDto.getDescription(),
                publicationDto.getUrl(),
                publicationDto.getLanguage(),
                publicationDto.getPublished_at(),
                publicationDto.getSource(),
                publicationDto.getCategories()
        );
    }

    public List<Publication> mapToPublications(final List<PublicationDto> publicationDtoList) {
        return publicationDtoList.stream()
                .map(this::mapToPublication)
                .collect(Collectors.toList());
    }
}
