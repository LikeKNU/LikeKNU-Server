package ac.knu.likeknu.collector.announcement.dto;

import lombok.Builder;
import org.jsoup.nodes.Element;

@Builder
public record StudentNewsElement(Element studentNewsTitleElement, Element studentNewsLinkElement,
                                 Element studentNewsDateElement, String campus, String kongjuUnivAddress) {
}
