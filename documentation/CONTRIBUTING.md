# Contributing to Our Project

## Branching Strategy

We use Git Flow for our branching strategy. The key branches are:

- `main`: The production-ready code.
- `develop`: The integration branch for features.

## Workflow

1. **Creating a Feature Branch**:

- Always branch off from `develop`.
  ```sh
  git checkout develop
  git pull origin develop
  git checkout -b feature/your-feature-name
  ```

2. **Pushing Changes**:

   ```sh
   git add .
   git commit -m "Description of your changes"
   git push origin feature/your-feature-name
   ```

3. **Creating a Merge Request**:

- Go to your repository in GitLab.
- Click on Merge Requests > New Merge Request.
- Select **feature/your-feature-name** as the source branch and **develop** as the target branch. (Make sure develop is the target branch).
- Assign reviewers and create the merge request.
- Provide a clear description of the changes using the **template** and link any relevant issues.

4. **Merging to 'develop'**:

- Wait for the reviewers to review before merging the changes.

5. **Merging to 'main'**:

- Only @JacobDomingo will be able to merge 'develop' to 'main' after all features are reviewed and tested.
