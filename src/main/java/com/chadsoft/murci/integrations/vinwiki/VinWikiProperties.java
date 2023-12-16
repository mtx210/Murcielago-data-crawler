package com.chadsoft.murci.integrations.vinwiki;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "integrations.vin-wiki")
@Getter
@Setter
class VinWikiProperties {

    private String urlTemplate;
    private List<String> murcielagoUserListsIds;
}