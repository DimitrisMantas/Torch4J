function edges = importEdges(filename, dataLines)

%IMPORTEDGES Import edges from a text file.
%  EDGES = IMPORTEDGES(FILENAME) reads edges from text file FILENAME 
%  for the default selection.  Returns the numeric data.
%
%  EDGES = IMPORTEDGES(FILE, DATALINES) reads edges for the specified
%  row interval(s) of text file FILENAME. Specify DATALINES as a positive
%  scalar integer or a N-by-2 array of positive scalar integers for
%  dis-contiguous row intervals.
%
%  Example:
%  edges = importEdges("C:\Graph\Edges.txt", [1, Inf]);
%
%  See also READTABLE.

%% Handle unexpected argument input.

% If dataLines is not specified, define defaults.
if nargin < 2
    dataLines = [1, Inf]; % Read all rows of data.
end

%% Set up the import options and import the edges.
importOptions = delimitedTextImportOptions("NumVariables", 10);

% Specify data range and delimiter.
importOptions.DataLines = dataLines;
importOptions.Delimiter = " ";

% Specify data column names and types.
importOptions.VariableNames = ["startVertex", "endVertex", "length", "SpeedLimit", "isForwards", "isBackwards", "roadType", "roadName1", "roadName2", "roadName3"];
importOptions.VariableTypes = ["double", "double", "double", "double", "double", "double", "string", "string", "string", "string"];
% Only import the first six columns of data.
importOptions.SelectedVariableNames = importOptions.VariableNames(1:6);

% Specify file level properties.
importOptions.ConsecutiveDelimitersRule = "join";
importOptions.EmptyLineRule = "read";
importOptions.ExtraColumnsRule = "ignore";
importOptions.LeadingDelimitersRule = "ignore";

% Specify variable properties.
importOptions = setvaropts(importOptions, importOptions.VariableNames(7:10), "EmptyFieldRule", "auto");
importOptions = setvaropts(importOptions, importOptions.VariableNames(7:10), "WhitespaceRule", "preserve");

% Import the data.
edges = readtable(filename, importOptions);

%% Convert the edges to an appropriate output data structure.
edges = table2array(edges);

end
