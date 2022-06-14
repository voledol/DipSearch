package project;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import project.model.Site;
import project.services.SiteService;

import java.util.List;

@AllArgsConstructor
public class PropertySiteSaver {
     public final SiteService siteService;


}
