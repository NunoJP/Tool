package domain.services;

import domain.entities.Converter;
import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.MetricsReport;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class OrganizationService {

    public OrganizationService() {
    }

    public boolean copyFilesThatMatch(ArrayList<File> selectedFiles, File targetFolder, ParsingProfileDo parsingProfile, MetricsProfileDo metricsProfile) {
        return performOperation(selectedFiles, targetFolder, parsingProfile, metricsProfile, false);
    }


    public boolean moveFilesThatMatch(ArrayList<File> selectedFiles, File targetFolder, ParsingProfileDo parsingProfile, MetricsProfileDo metricsProfile) {
        return performOperation(selectedFiles, targetFolder, parsingProfile, metricsProfile, true);
    }

    private boolean performOperation(ArrayList<File> selectedFiles, File targetFolder, ParsingProfileDo parsingProfile, MetricsProfileDo metricsProfile, boolean isMove) {
        if(selectedFiles == null || selectedFiles.isEmpty()) {
            return false;
        }

        try {
            for (File selectedFile : selectedFiles) {

                FileAnalysisService fileAnalysisService = new FileAnalysisService(selectedFile, Converter.toDomainObject(parsingProfile),
                        Converter.toDomainObject(metricsProfile));
                FileAnalysisMetricsService fileAnalysisMetricsService = new FileAnalysisMetricsService(fileAnalysisService, Converter.toDomainObject(metricsProfile));
                MetricsReport metricsReport = fileAnalysisMetricsService.getMetricsReport();
                metricsReport.getKwdThresholdData();

                if(!metricsReport.getWarningsData().isEmpty()) {
                    if (isMove) {
                        Files.move(selectedFile.toPath(),
                                new File(targetFolder.toPath() + File.separator + selectedFile.getName()).toPath(),
                                StandardCopyOption.REPLACE_EXISTING);
                    } else {
                        Files.copy(selectedFile.toPath(),
                                new File(targetFolder.toPath() + File.separator + selectedFile.getName()).toPath(),
                                StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }


}
