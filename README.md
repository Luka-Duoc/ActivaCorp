# Activa Corp

**Activa Corp** es una aplicación móvil nativa para Android diseñada para descubrir, organizar y gestionar oportunidades de activación corporativa desde un solo lugar. Provee una plataforma centralizada para que ejecutivos y supervisores comerciales administren organizaciones, eventos y el embudo de oportunidades de negocio.

## 🚀 Características Principales

* **Autenticación y Roles de Usuario:** Sistema de login y registro. Asignación automática de roles (Ejecutivo, Supervisor, Marketing) basado en el dominio del correo electrónico corporativo (ej. `@ejecutivo.cl`, `@supervisor.cl`).
* **Dashboard de Gestión:** Panel de resumen con contadores analíticos en tiempo real (oportunidades pendientes, de prioridad alta y confirmadas).
* **Gestión de Organizaciones (CRUD):** Registro y administración de entidades clasificadas por tipo (Empresa, Municipalidad, Institución Pública, etc.), rubro y ubicación.
* **Gestión de Eventos (CRUD):** Planificación de eventos asociados a organizaciones y comunas. Incluye rangos de fechas, aforos, costos referenciales y requisitos.
* **Gestión de Oportunidades (CRUD):** Seguimiento del embudo comercial mediante estados de gestión (Pendiente, Propuesta Enviada, Confirmada), valoración de potencial comercial y niveles de prioridad.
* **Escáner QR Integrado:** Funcionalidad basada en ML Kit y CameraX para escanear y registrar automáticamente eventos a partir de códigos QR con formato JSON.
* **Datos Pre-cargados (Seeders):** La base de datos se inicializa con un catálogo completo de regiones, comunas, organizaciones, eventos y oportunidades de prueba para facilitar su uso inmediato.

## 🛠️ Tecnologías Utilizadas

La aplicación está construida utilizando el ecosistema moderno de desarrollo de Android:

* **Lenguaje:** [Kotlin](https://kotlinlang.org/)
* **Interfaz de Usuario:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material Design 3)
* **Arquitectura:** MVVM (Model-View-ViewModel) + Patrón Repositorio
* **Base de Datos Local:** [Room](https://developer.android.com/training/data-storage/room) (SQLite) con relaciones mediante Claves Foráneas (Foreign Keys).
* **Asincronía y Reactividad:** Corrutinas (Coroutines) y flujos de estado (`StateFlow`, `Flow`).
* **Navegación:** Navigation Compose
* **Cámara y Visión:** [CameraX](https://developer.android.com/training/camerax) y [Google ML Kit Barcode Scanning](https://developers.google.com/ml-kit/vision/barcode-scanning).

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una arquitectura **Offline-First**, garantizando que la aplicación sea 100% funcional sin conexión a internet. La estructura de paquetes es la siguiente:

```text
com.example.activacorp
│
├── data/           # Capa de datos (Room Database, DAOs, Entidades, Seeders)
├── model/          # Modelos de dominio, DTOs, Enums y Estados de Interfaz (UiState)
├── navigation/     # Grafo de navegación de Jetpack Compose
├── repository/     # Patrón repositorio (Abstracción de fuentes de datos)
├── ui/             # Capa de presentación
│   ├── screen/     # Pantallas composables (Home, Login, Cuenta, Eventos, Organizaciones...)
│   └── theme/      # Configuración de Material Theme 3 (Colores, Tipografía)
├── utils/          # Utilidades (Gestión de permisos de cámara, analizador QR)
└── viewmodel/      # ViewModels (Lógica de negocio y gestión de estado)
```

## ⚙️ Instalación y Uso

### Requisitos Previos
* Android Studio (versión recomendada: Iguana o superior).
* SDK de Android (API 24+).
* Dispositivo físico o emulador con acceso a cámara (para probar el escáner QR).

### Pasos para compilar
1. Clona este repositorio:
   ```bash
   git clone [https://github.com/tu-usuario/ActivaCorp.git](https://github.com/tu-usuario/ActivaCorp.git)
   ```
2. Abre el proyecto en **Android Studio**.
3. Espera a que Gradle sincronice las dependencias.
4. Presiona **Run** (`Shift + F10`) para instalar la aplicación en tu emulador o dispositivo físico.

### Credenciales de Prueba (Roles)
Gracias al sistema de validación de dominios, puedes crear cuentas con distintos roles directamente desde la pantalla de registro usando los siguientes dominios:
* Ejecutivo Comercial: `nombre@ejecutivo.cl` o `nombre@comercial.cl`
* Supervisor Comercial: `nombre@supervisor.cl`
* Marketing: `nombre@marketing.cl`

## 👥 Equipo de Desarrollo
* Lucas Ortiz
* Ignacio Battistoni

Asignatura: Desarrollo de aplicaciones móviles (007D).
Docente: Vicente Julio Zapata Valenzuela.
