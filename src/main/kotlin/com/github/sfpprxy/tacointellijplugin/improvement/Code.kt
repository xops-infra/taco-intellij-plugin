package com.github.sfpprxy.tacointellijplugin.improvement

import com.intellij.openapi.diagnostic.thisLogger

data class Code(val content: String, val language: Language)

enum class Language {
    BASH,
    GO,
    JAVA,
    JAVASCRIPT,
    KOTLIN,
    PHP,
    PYTHON,
    RUST,
    SQL,
    TYPESCRIPT,
    UNKNOWN;

    companion object {
        fun from(type: String): Language {
            return when (type) {
                "Bash" -> BASH
                "Go" -> GO
                "Java" -> JAVA
                "JavaScript" -> JAVASCRIPT
                "Kotlin" -> KOTLIN
                "PHP" -> PHP
                "Python" -> PYTHON
                "Rust" -> RUST
                "SQL" -> SQL
                "TypeScript" -> TYPESCRIPT
                else -> {
                    thisLogger().warn("Unknown language type: $type")
                    UNKNOWN
                }
            }
        }
    }
}
