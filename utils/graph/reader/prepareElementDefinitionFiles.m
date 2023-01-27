


%% Import the source vertex and edge definition files.
% The following if-checks skip the import process if the corresponding data sets are already in the workspace.

% % The shape of the source vertex dataset is 8,500,875 x 3.
% if ~(exist("vertices", "var")) || ~ (isequal(size(vertices), [8500875, 3]))
%     vertices = importVertices("C:\Users\Dimit\IdeaProjects\Torch\utils\graph\grc_graph_vertices_8500875.txt");
% end

% The shape of the source edge dataset is 8,809,699 x 6.
if ~(exist("edges", "var")) || ~ (isequal(size(edges), [8809699, 6]))
    edges = importEdges("C:\Users\Dimit\IdeaProjects\Torch\utils\graph\source\grc_graph_edges_8809699_source.txt");
end

%% Calculate the travel time of each edge.
numEdges = length(edges);
travelTimes = zeros(numEdges, 1);
% The equivalent vectorized code exceeds the maximum default variable size.
for i = 1:numEdges
    % All edge weights should be integer values.
    travelTimes(i) = ceil(edges(i, 3) ...  % m
                       / ((1000 / 3600) * edges(i, 4)));  % m/s
end

% The shape of the resulting dataset will be equal to N x 10, where N is total the number of edges remaining after filtering the corresponding dataset. One additional column has been added between the seventh and eight columns, and the eighth column has then been removed.
edges = [edges(:, 1:3), travelTimes edges(:, 5:6)];


%% Write the vertex and edge datasets, as well as the above-mentioned lookup table, to element definition files.
% writematrix(vertices, "C:\Users\Dimit\IdeaProjects\Torch\utils\resources\grc\grc_graph_vertices_8500875.txt", Delimiter=" ", WriteMode="overwrite");
writematrix(edges, "C:\Users\Dimit\IdeaProjects\Torch\utils\resources\grc\grc_graph_edges_8809699.txt", Delimiter=" ", WriteMode="overwrite");

%% Clean up the workspace.
clear i numEdges