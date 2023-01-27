function vertices = importVertices(filename, dataLines)

%IMPORTVERTICES Import vertices from a text file.
%  VERTICES = IMPORTVERTICES(FILENAME) reads vertices from text file 
%  FILENAME for the default selection.  Returns the numeric data.
%
%  VERTICES = IMPORTVERTICES(FILE, DATALINES) reads data for the specified
%  row interval(s) of text file FILENAME. Specify DATALINES as a
%  positive scalar integer or a N-by-2 array of positive scalar integers
%  for dis-contiguous row intervals.
%
%  Example:
%  vertices = importVertices("C:\Graph\Vertices.txt", [1, Inf]);
%
%  See also READTABLE.

%% Handle unexpected argument input.

% If dataLines is not specified, define defaults.
if nargin < 2
    dataLines = [1, Inf]; % Read all rows of data.
end

%% Set up the import options and import the vertices.
importOptions = delimitedTextImportOptions("NumVariables", 3);

% Specify range and delimiter.
importOptions.DataLines = dataLines;
importOptions.Delimiter = " ";

% Specify data column names and types.
importOptions.VariableNames = ["id", "lat", "lon"];
importOptions.VariableTypes = ["double", "double", "double"];

% Specify file level properties.
importOptions.ConsecutiveDelimitersRule = "join";
importOptions.EmptyLineRule = "read";
importOptions.ExtraColumnsRule = "ignore";
importOptions.LeadingDelimitersRule = "ignore";

% Import the data.
vertices = readtable(filename, importOptions);

%% Convert the vertices to an appropriate output data structure.
vertices = table2array(vertices);

end
