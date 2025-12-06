package com.sysnormal.libs.utils;

public final class Constants {
    private Constants() {}

    public static final class Javax {
        private Javax() {};

        public static final class SQL {
            private SQL() {};

            public static final class DATASOURCE {
                private DATASOURCE() {};

                public static final String CLASSNAME = "DataSource";
            }
        }
    }

    public static final class Spring {
        private Spring(){};

        public static final class DATASOURCE {
            private DATASOURCE() {};

            public static final String PROPERTY = "spring.datasource";
        }
    }

    public static final class SpringFramework {
        private SpringFramework(){};

        public static final class TRANSACTION {
            private TRANSACTION() {};

            public static final class TRANSACTIONMANAGER {
                private TRANSACTIONMANAGER() {};

                public static final String CLASSNAME = "TransactionManager"; //dont use TransactionManager.class.getSimpleName(), this cause error on use in annotations
            }
        }
    }

    public static final class Hibernate {
        private Hibernate() {}

        public static final class DIALECT {
            private DIALECT() {};

            public static final String PROPERTY = "hibernate.dialect";
        }

        public static final class HBM2DDL {
            private HBM2DDL() {}

            public static final class AUTO {
                private AUTO(){};

                public static final String PROPERTY = org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
                public static final class VALUES {
                    private VALUES() {}
                    @Deprecated(since = "Spring Boot 4+", forRemoval = false)
                    public static final String NONE = "none";
                    public static final String VALIDATE = "validate";
                    public static final String CREATE = "create";
                    public static final String UPDATE = "update";
                }
            }
        }

        public static final class CONNECTION {
            private CONNECTION() {}

            public static final class PROVIDER_DISABLES_AUTOCOMMIT {
                private PROVIDER_DISABLES_AUTOCOMMIT() {};
                public static final String PROPERTY = "hibernate.connection.provider_disables_autocommit";
            }
        }

        public static final class BOOT {
            private BOOT() {}

            public static final class ALLOW_JDBC_METADATA_ACCESS {
                private ALLOW_JDBC_METADATA_ACCESS() {};
                public static final String PROPERTY = "hibernate.boot.allow_jdbc_metadata_access";
            }
        }

        public static final class GLOBALLY_QUOTED_IDENTIFIERS {
            private GLOBALLY_QUOTED_IDENTIFIERS() {};
            public static final String PROPERTY = "hibernate.globally_quoted_identifiers";
        }

        public static final class PHYSICAL_NAMING_STRATEGY {
            private PHYSICAL_NAMING_STRATEGY(){};
            public static final String PROPERTY = "hibernate.physical_naming_strategy";
        }


    }

    public static final class Jakarta {
        private Jakarta() {}

        public static final class PERSISTENCE {
            private PERSISTENCE() {}

            public static final class ENTITYMANAGERFACTORY {
                private ENTITYMANAGERFACTORY() {};

                public static final String CLASSNAME = "EntityManagerFactory"; //dont use EntityManagerFactory.class.getSimpleName(), this cause error on use in annotations
            }

            public static final class SCHEMA_GENERATION {
                private SCHEMA_GENERATION() {}

                public static final class DATABASE {
                    private DATABASE(){};

                    public static final class ACTION {
                        public static final String PROPERTY = org.hibernate.cfg.AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION;

                        public static final class VALUES {
                            private VALUES() {}
                            public static final String NONE = "none";
                            public static final String VALIDATE = "validate";
                            public static final String CREATE = "create";
                            public static final String UPDATE = "update";
                        }
                    }
                }

            }
        }
    }
}