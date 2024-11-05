package spaceships.crud.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

public abstract class AbstractArchitectureTest {

  protected static final String BASE_PACKAGE = "spaceshipscrud.crud";
  protected static final String DOMAIN_PACKAGE = BASE_PACKAGE + ".domain";
  protected static final String ANY_DOMAIN_PACKAGE = DOMAIN_PACKAGE + "..";
  protected static final String APPLICATION_PACKAGE = BASE_PACKAGE + ".application";
  protected static final String ANY_APPLICATION_PACKAGE = APPLICATION_PACKAGE + "..";
  protected static final String INFRASTRUCTURE_PACKAGE = BASE_PACKAGE + ".infrastructure";
  protected static final String ANY_INFRASTRUCTURE_PACKAGE = INFRASTRUCTURE_PACKAGE + "..";

  protected static final JavaClasses importedClasses =
      new ClassFileImporter().importPackages(BASE_PACKAGE);
}
