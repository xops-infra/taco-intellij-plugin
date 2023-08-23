package com.github.sfpprxy.tacointellijplugin.improvement

@Suppress("unused")
enum class ImprovementScope {
    SNIPPET,
    FILE,
    DIRECTORY,
    PROJECT,
}

data class ProjectObject(
    val path: String,
    val isDirectory: Boolean,
    val size: Int,
    val code: Code?,
)

enum class Rule {
    AMBIGUOUS_NAME,
    EARLY_RETURN,
    REDUCE_CODE_DUPLICATION,
    USE_ENCAPSULATION,
    SIMPLIFY_LOOP_SYNTAX;

    companion object {
        fun defaultSet(): List<Rule> {
            return listOf(
                AMBIGUOUS_NAME,
                EARLY_RETURN,
                REDUCE_CODE_DUPLICATION,
                USE_ENCAPSULATION,
                SIMPLIFY_LOOP_SYNTAX
            )
        }
    }
}

// input of backend api
data class ImprovementContext(
    val scope: ImprovementScope,
    val projectObjects: List<ProjectObject>,
    val rules: List<Rule>,
)

// output of backend api
data class Improvement(
    val improvementContext: ImprovementContext,
    // rules that `improvementContext.projectObjects` violates and `improvedProjectObjects` satisfies
    val rulesHit: List<Rule>,
    val improvedProjectObjects: List<ProjectObject>,
    val comment: String,
)
