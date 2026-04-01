# Radioactive Decay Volume Calculator / Calculadora de Decaimiento Radiactivo

[🇪🇸 Leer en Español](#español) | [🇬🇧 Read in English](#english)

---

<a name="english"></a>
## 🇬🇧 English

A Java-based, cross-platform desktop application (Swing) designed for nuclear medicine departments and radiopharmacies. It precisely calculates the required syringe volume for a target radioactive dose over time, automatically adjusting for the physical decay of the isotope (e.g., 99mTc).

### Clinical Features
* **Time-Zero Volume ($V_0$):** Calculates the exact ideal volume to inject at the moment of preparation.
* **Injection Timetable:** Generates a precise, minute-by-minute wait-time table mapped to standard 0.1 ml syringe increments to maintain a constant target dose.
* **Native Multilingual Support:** The user interface can be dynamically switched between **English, Español, Français, Português, Deutsch, Italiano, العربية (Arabic), हिन्दी (Hindi), Kiswahili, Hausa, 中文 (Chinese), and 日本語 (Japanese)**.
* **Clinical PDF Export:** Automatically generates an A4 PDF report detailing the input parameters, institution name, and the full injection timetable using iText.

### Tech Stack
* **Language:** Java 21
* **UI Framework:** Java Swing
* **Build Tool:** Apache Maven
* **PDF Generation:** iText 5.5.13


### 🚀 How to Run

**Option 1: Windows (No installation required - Recommended)**
You don't need to install Java to run this version.
1. Go to the [Releases](https://github.com/GamezConZ/calculadora-decaimiento/releases) page.
2. Download the `Calculadora_Decaimiento_Windows.zip` file.
3. Extract the ZIP file anywhere on your computer.
4. Open the extracted folder and double-click the `Calculadora_Decaimiento.exe` file.

**Option 2: Universal / Linux / Mac (Requires Java 21+)**
1. Ensure your system has Java 21 or higher installed (e.g., [Eclipse Adoptium](https://adoptium.net/)).
2. Download the `calculadora-decaimiento-XXX.jar` file from the [Releases](https://github.com/GamezConZ/calculadora-decaimiento/releases) page.
3. Double-click the `.jar` file or run it via terminal:
   ```bash
   java -jar calculadora-decaimiento-XXX.jar


### License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

<a name="español"></a>
## 🇪🇸 Español

Aplicación de escritorio multiplataforma desarrollada en Java (Swing) orientada a servicios de medicina nuclear y radiofarmacia. Permite calcular con precisión los volúmenes de inyección incrementales necesarios para administrar una dosis objetivo constante, compensando el decaimiento físico del isótopo (ej. 99mTc) a lo largo del tiempo.

### Características Clínicas
* **Cálculo de Volumen Cero ($V_0$):** Determina el volumen ideal a inyectar en el momento exacto de la preparación.
* **Cronograma Escalonado:** Genera una tabla de tiempos de espera precisos (minutos) asociados a incrementos de 0.1 ml en la jeringa para mantener la dosis requerida.
* **Soporte Multilingüe Nativo:** La interfaz de usuario puede alternarse dinámicamente entre **Inglés, Español, Francés, Portugués, Alemán, Italiano, Árabe, Hindi, Suajili, Hausa, Chino y Japonés**.
* **Exportación Clínica a PDF:** Generación automatizada de reportes en PDF formato A4 listos para imprimir, incluyendo datos de la institución, parámetros de entrada y la tabla de inyección mediante iText.

### Tecnologías
* **Lenguaje:** Java 21
* **Interfaz Visual:** Java Swing
* **Gestor de dependencias:** Apache Maven
* **Generación de PDF:** iText 5.5.13

### 🚀 Cómo Ejecutar

**Opción 1: Windows (Portable, no requiere instalación - Recomendado)**
No necesitas tener Java instalado en tu computadora para usar esta versión.
1. Ve a la pestaña de [Releases](https://github.com/GamezConZ/calculadora-decaimiento/releases).
2. Descarga el archivo `Calculadora_Decaimiento_Windows.zip`.
3. Descomprime el archivo ZIP en cualquier lugar de tu computadora (ej. Escritorio).
4. Abre la carpeta extraída y haz doble clic en el archivo `Calculadora_Decaimiento.exe`.

**Opción 2: Universal / Linux / Mac (Requiere Java 21+)**
1. Asegúrate de tener instalado Java 21 o superior (recomendamos [Eclipse Adoptium](https://adoptium.net/)).
2.  Descarga el archivo `calculadora-decaimiento-XXX.jar` desde la pestaña de [Releases](https://github.com/GamezConZ/calculadora-decaimiento/releases).
3. Haz doble clic en el archivo `.jar` o ejecútalo desde la terminal:
   ```bash
   java -jar calculadora-decaimiento-XXX.jar


### Licencia
Este proyecto está bajo la Licencia MIT - mira el archivo [LICENSE](LICENSE) para más detalles.
