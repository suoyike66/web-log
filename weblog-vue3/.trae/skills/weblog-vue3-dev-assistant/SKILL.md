---
name: "weblog-vue3-dev-assistant"
description: "Provides development assistance for the weblog-vue3 project, including code quality checks, performance optimization, and development efficiency improvements. Invoke when working on project development, optimization, or maintenance."
---

# Weblog Vue3 Development Assistant

This skill provides comprehensive assistance for the weblog-vue3 project development, focusing on the optimization areas identified during project analysis.

## When to Invoke

Invoke this skill when:
- Working on code optimization for the weblog-vue3 project
- Adding new features or components
- Debugging performance issues
- Improving code quality and maintainability
- Preparing for production deployment
- Need guidance on best practices for the project

## Key Features

### 1. Code Quality Analysis
- **Component Structure Review**: Analyzes Vue component structure and suggests improvements
- **Code Style Enforcement**: Checks for consistent code style and formatting
- **Modularity Assessment**: Evaluates code organization and suggests better modularization
- **Component Reusability**: Identifies opportunities for component reuse

### 2. Performance Optimization
- **Bundle Size Analysis**: Identifies large dependencies and suggests optimization strategies
- **Rendering Performance**: Analyzes component rendering and suggests improvements
- **Memory Usage**: Identifies potential memory leaks and optimization opportunities
- **Resource Loading**: Suggests improvements for image loading and static resources

### 3. Development Efficiency
- **API Call Optimization**: Reviews API calling patterns and suggests improvements
- **State Management**: Evaluates Pinia store usage and suggests optimizations
- **Routing Optimization**: Reviews router configuration and suggests improvements
- **Build Process**: Analyzes build configuration and suggests optimizations

### 4. Security Enhancement
- **Authentication Flow**: Reviews authentication and authorization implementation
- **Input Validation**: Checks for proper input validation and sanitization
- **Token Management**: Evaluates JWT token handling and storage

### 5. User Experience
- **Responsiveness Analysis**: Evaluates responsive design implementation
- **Interaction流畅度**: Analyzes user interactions and suggests improvements
- **Loading States**: Reviews loading indicators and suggests enhancements
- **Accessibility**: Checks for basic accessibility compliance

## Usage Examples

### Example 1: Code Quality Check
```
Analyze the code quality of the CountTo.vue component and suggest improvements.
```

### Example 2: Performance Optimization
```
Identify performance bottlenecks in the admin dashboard page and suggest optimizations.
```

### Example 3: Build Optimization
```
Analyze the current build configuration and suggest ways to reduce bundle size.
```

### Example 4: API Call Optimization
```
Review the API calling patterns in the article list page and suggest improvements.
```

### Example 5: Security Review
```
Check the authentication flow and suggest security enhancements.
```

## Optimization Priorities

When providing optimization suggestions, the assistant follows these priorities:

1. **Critical Performance Issues**: Issues that significantly impact page load time or user interaction
2. **User Experience Bottlenecks**: Issues that affect user perception of performance
3. **Build Size Optimization**: Opportunities to reduce bundle size and improve load time
4. **Code Quality Improvements**: Enhancements that improve maintainability and readability
5. **Security Enhancements**: Measures to improve application security

## Project-Specific Knowledge

This assistant has specific knowledge of:
- Vue 3 Composition API best practices
- Element Plus component library usage
- Pinia state management
- Vite build configuration
- axios API integration
- The project's specific directory structure and architecture
- Identified optimization opportunities from initial analysis

## Output Format

When providing analysis and suggestions, the assistant will:
1. Clearly identify the issue or opportunity
2. Explain the impact on the project
3. Provide specific, actionable recommendations
4. Include code examples when appropriate
5. Suggest verification methods to confirm improvements

## Verification Methods

For each optimization suggestion, the assistant will recommend appropriate verification methods:
- **Performance Metrics**: Using browser dev tools performance panel
- **Bundle Analysis**: Using Vite's build analysis tools
- **Lighthouse Audits**: For overall performance and accessibility
- **Manual Testing**: For user experience improvements
- **Code Reviews**: For code quality enhancements

This assistant is designed to help maintain and improve the weblog-vue3 project by providing targeted, actionable guidance based on best practices and project-specific knowledge.